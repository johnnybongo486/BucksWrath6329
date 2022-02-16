package frc.robot.Commands.Auto;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class TurnToAngle extends CommandBase {

    public double turnThrottle = 0;
    public double forwardThrottle = 0;
    public double rotateGain = 0;

    public double kPgain = 0.008;
    public double lPgain = 0.008; /* percent throttle per degree of error */ //was .04
	public double kDgain = 0; /* percent throttle per angular velocity dps */
	public double kMaxCorrectionRatio = 0.50; /* cap corrective turning throttle to 30 percent of forward throttle */
    public double sPgain = 0.04;

    public double targetAngle = 0;
    public double currentAngle = 0;
    public double currentAngularRate = 0;
    public double currentDistance = 0;
    public double error = 0;
    public double acceptableError = 5;
    public double maxSpeed = 0;

    public TurnToAngle(int angle, double maxSpeed) {
        addRequirements(RobotContainer.drivetrain);
        this.targetAngle = angle;   
        this.maxSpeed = maxSpeed;
        currentAngle = RobotContainer.drivetrain.getAngle();
        // error = Math.abs(targetAngle - currentAngle);
        // if(error > 30) {
        //     kPgain = lPgain;
         //}
        //else  {
        //    kPgain = sPgain;
        //}
    }

    public void initialize() {

    }

    public void execute() {
        currentAngle = RobotContainer.drivetrain.getAngle();
        currentAngularRate = RobotContainer.drivetrain.getRoll();
        turnThrottle = (targetAngle - currentAngle) * kPgain - (currentAngularRate) * kDgain;
        error = Math.abs(targetAngle - currentAngle);
			/* the max correction is the forward throttle times a scalar,
			 * This can be done a number of ways but basically only apply small turning correction when we are moving slow
			 * and larger correction the faster we move.  Otherwise you may need stiffer pgain at higher velocities. */
		double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, maxSpeed);
        right = Cap(right, maxSpeed);
        RobotContainer.drivetrain.drive(ControlMode.PercentOutput, left, right);
    }

    public boolean isFinished() {
        return error < acceptableError;
    }

    protected void end() {
        RobotContainer.drivetrain.stopDrivetrain();
    }

    protected void interrupted(){
        end();
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
