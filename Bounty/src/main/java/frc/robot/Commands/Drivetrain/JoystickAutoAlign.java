package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.*;

public class JoystickAutoAlign extends Command {

    private double tx;
    private double[] camtran = new double[6];
    private double angle;
    private double left_command;
    private double right_command;
    private double speed;
    private double slide;

    public double turnThrottle = 0;
    public double forwardThrottle = 0;
    public double rotateGain = 0;
    public double currentAngularRate = 0;

    public double kPgain = 0.01; /* percent throttle per degree of error */ //was .04
	public double kDgain = 0.0001; /* percent throttle per angular velocity dps */
	public double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */
    public int _printLoops = 0;

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimCamtran;
    NetworkTable table;
    NetworkTableInstance Inst;

    public JoystickAutoAlign() {
        requires(Robot.Drivetrain);

        slide = 0;
        left_command = 0;
        right_command = 0;

    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("camMode").setNumber(0);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight-shooter");
        prelimtx = table.getEntry("tx");
        prelimCamtran = table.getEntry("camtran");
    }

    protected void execute() {
        forwardThrottle = -Robot.oi.getDriverLeftStickY();
        slide = Robot.oi.getDriverLeftStickX();
        tx = prelimtx.getDouble(0);
        angle = camtran[1];
        currentAngularRate = Robot.Drivetrain.getRoll();

        turnThrottle = (tx) * kPgain - (currentAngularRate) * kDgain;
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);

        Robot.Drivetrain.drive(ControlMode.PercentOutput, left, right);
        Robot.SlideDrive.drive(slide);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("camMode").setNumber(1);
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("ledMode").setNumber(1);
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