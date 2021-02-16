package frc.robot.Auto.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveStraight extends Command {

    public double turnThrottle = 0;
    public double forwardThrottle = 0;
    public double rotateGain = 0;
    public double kPgainSpeed = 0.0175;
    public double kIgainSpeed = 0.000005;
    public double kDgainSpeed = 0.05;
    public double kIZone = 12;
    public double kPgain = 0.04; /* percent throttle per degree of error */ //was .04
	public double kDgain = 0.00004; /* percent throttle per angular velocity dps */
	public double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */
    public double targetAngle = 0;
    public double currentAngle = 0;
    public double currentAngularRate = 0;
    public double targetDistance = 0;
    public double currentDistance = 0;
    public double acceptableError = 1000;
    public double AVGerror = 0;
    public double maxSpeed = 0;
    public double errorSum = 0;
    public double lastTimeStamp = 0;
    public double dt = 0;
    public double absError = 0;
    public double errorRate = 0;
    public double lastError = 0;
    public double error = 0;

   public DriveStraight(double distance, double maxSpeed) {
        requires(Robot.Drivetrain);
        this.targetDistance = distance *12;   
        this.maxSpeed = maxSpeed;
   }

   protected void initialize() {
        targetAngle = Robot.Drivetrain.getAngle();
        Robot.Drivetrain.resetDriveEncoders();
        errorSum = 0;
        error = 0;
        absError = 0;
        errorRate = 0;
        lastError = 0;
        lastTimeStamp = Timer.getFPGATimestamp();
   }

   protected void execute() {
        // Get current values
        currentAngle = Robot.Drivetrain.getAngle();
        currentAngularRate = Robot.Drivetrain.getRoll();
        currentDistance = Robot.Drivetrain.getDistance();

        // Find Change in Time for kI
        dt = Timer.getFPGATimestamp() - lastTimeStamp;

        // Calculate P
        error = targetDistance - currentDistance;
        absError = Math.abs(targetDistance - currentDistance);  // for when to finish
     
        // Calculate I if in IZone
         if (Math.abs(error) < kIZone) {
          errorSum += error * dt;
        }

        // Calculate D
        errorRate = (error - lastError) / dt;

        // Calculate Forward Throttle with PID
        forwardThrottle = error * kPgainSpeed + errorSum * kIgainSpeed + errorRate * kDgainSpeed;

        // Calculate needed turn to hold line
        turnThrottle = (targetAngle - currentAngle) * kPgain - (currentAngularRate) * kDgain;
			/* the max correction is the forward throttle times a scalar,
			 * This can be done a number of ways but basically only apply small turning correction when we are moving slow
			 * and larger correction the faster we move.  Otherwise you may need stiffer pgain at higher velocities. */
		double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        forwardThrottle = Cap(forwardThrottle, maxSpeed);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, maxSpeed);
        right = Cap(right, maxSpeed);

        // Write values to drivetrain
        Robot.Drivetrain.setPower(left, right);

        // Update Variables to Current
        lastTimeStamp = Timer.getFPGATimestamp();
        lastError = error;
   }

   protected boolean isFinished() {
        return absError < acceptableError;
   }

   protected void end() {
        Robot.Drivetrain.stopDrivetrain();
   }

   protected void interrupted(){

   }
   
   double MaxCorrection(double forwardThrot, double scalor) {
        /* make it positive */
        if(forwardThrot < 0) {forwardThrot = -forwardThrot;}
        /* max correction is the current forward throttle scaled down */
        forwardThrot *= scalor;
        /* ensure caller is allowed at least 10% throttle,
        * regardless of forward throttle */
        if(forwardThrot < 0.30)
        return 0.30;
        return forwardThrot;    
    }

    double Cap(double value, double peak) {
        if (value < -peak)
            return -peak;
        if (value > +peak)
            return +peak;
        return value;
    }
  
}

