package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.*;

public class JoystickAutoAlignIntake extends Command {

    private double tx;
    private double[] camtran = new double[6];
    private double angle;
    private double slide;

    private double turnThrottle = 0;
    private double forwardThrottle = 0;
    private double currentAngularRate = 0;

    private double kPgain = 0.01; /* percent throttle per degree of error */ //was .04
    private double kIgain = 0.0;
    private double kDgain = 0.0001; /* percent throttle per angular velocity dps */
    private double errorSum = 0;
    private double lastTimeStamp = 0;
	private double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimCamtran;
    NetworkTable table;
    NetworkTableInstance Inst;

    public JoystickAutoAlignIntake() {
        requires(Robot.Drivetrain);
        slide = 0;
    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight-intake").getEntry("camMode").setNumber(0);
        Timer.delay(0.1);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight-intake");
        prelimtx = table.getEntry("tx");
        prelimCamtran = table.getEntry("camtran");
        errorSum = 0;
        lastTimeStamp = Timer.getFPGATimestamp();
    }

    protected void execute() {
        forwardThrottle = -Robot.oi.getDriverLeftStickY();
        slide = Robot.oi.getDriverLeftStickX();
        tx = prelimtx.getDouble(0);
        angle = camtran[1];
        currentAngularRate = Robot.Drivetrain.getRoll();
        double dt = Timer.getFPGATimestamp() - lastTimeStamp;
        errorSum += tx * dt;

        turnThrottle = (tx) * kPgain + kIgain * errorSum + (currentAngularRate) * kDgain; // should be added
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);
        
        Robot.Drivetrain.drive(ControlMode.PercentOutput, left, right);
        Robot.SlideDrive.drive(slide);

        lastTimeStamp = Timer.getFPGATimestamp();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        NetworkTableInstance.getDefault().getTable("limelight-intake").getEntry("camMode").setNumber(1);
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