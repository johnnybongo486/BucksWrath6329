package frc.robot.Auto.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import edu.wpi.first.networktables.*;

public class JoystickAutoAlign extends Command {

    private double tx;
    private double left_command;
    private double right_command;
    private double speed;

    public double turnThrottle = 0;
    public double forwardThrottle = 0;
    public double rotateGain = 0;
    public double currentAngularRate = 0;

    public double kPgain = 0.02; /* percent throttle per degree of error */ //was .04
	public double kDgain = 0.0001; /* percent throttle per angular velocity dps */
	public double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */
    public int _printLoops = 0;

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimCamtran;
    NetworkTable table;
    NetworkTableInstance Inst;

    public JoystickAutoAlign() {
        requires(Robot.Drivetrain);
        left_command = 0;
        right_command = 0;
    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
        prelimtx = table.getEntry("tx");
    }

    protected void execute() {
        forwardThrottle = -Robot.oi.getDriverLeftStickY();
        forwardThrottle = forwardThrottle / 3;
        tx = prelimtx.getDouble(0)+1;
        currentAngularRate = Robot.Drivetrain.getRoll();

        turnThrottle = (tx) * kPgain - (currentAngularRate) * kDgain;
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);
        Robot.Drivetrain.setPower(left, right);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
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