package frc.robot.Auto.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


import edu.wpi.first.networktables.*;

public class NewVisionDrive extends Command {

    private double tx;
    private double ta;
    
    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimta;
    NetworkTable table;
    NetworkTableInstance Inst;

    public double left = 0;
    public double right = 0;
    public double turnThrottle = 0;
    public double forwardThrottle = 0;
    public double rotateGain = 0;
    public double kPgainSpeed = 0.00005;
    public double kIgainSpeed = 0;
    public double kDgainSpeed = 0;
    public double kIZone = 4096;
    public double kPgain = 0.004; /* percent throttle per degree of error */ //was .04
	public double kDgain = 0.00004; /* percent throttle per angular velocity dps */
	public double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */
    public double targetAngle = 0;
    public double currentAngle = 0;
    public double currentAngularRate = 0;
    public double targetArea = 50;  // Set Area Here
    public double acceptableError = 5;  // Set Acceptable Error
    public double errorSum = 0;
    public double lastTimeStamp = 0;
    public double dt = 0;
    public double absError = 0;
    public double errorRate = 0;
    public double lastError = 0;
    public double error = 0;

    public NewVisionDrive() {
        requires(Robot.Drivetrain);
    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
        prelimtx = table.getEntry("tx");
        prelimta = table.getEntry("ta");
        left = 0;
        right = 0;
        errorSum = 0;
        error = 0;
        absError = 0;
        errorRate = 0;
        lastError = 0;
        lastTimeStamp = Timer.getFPGATimestamp();
    }

    protected void execute() {
        tx = prelimtx.getDouble(0);
        ta = prelimta.getDouble(0);

        // Find Change in Time for kI
        dt = Timer.getFPGATimestamp() - lastTimeStamp;

        // Calculate P
        error = targetArea - ta;
        absError = Math.abs(targetArea - ta);  // for when to finish
             
        // Calculate I if in IZone
        if (Math.abs(error) < kIZone) {
            errorSum += error * dt;
        }
        
        // Calculate D
        errorRate = (error - lastError) / dt;
        
        // Calculate Forward Throttle with PID
        forwardThrottle = error * kPgainSpeed + errorSum * kIgainSpeed + errorRate * kDgainSpeed;
        
        // Calculate Turn Throttle
        turnThrottle = (tx) * kPgain - (currentAngularRate) * kDgain;

        // Send Values to Drivetrain
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        left = forwardThrottle - turnThrottle;
        right = forwardThrottle + turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);
        Robot.Drivetrain.setPower(left, right);

        // Update Variables to Current
        lastTimeStamp = Timer.getFPGATimestamp();
        lastError = error;
    }

    protected boolean isFinished() {
        return false || absError < acceptableError;
    }

    protected void end() {
        Robot.Drivetrain.stopDrivetrain();
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
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