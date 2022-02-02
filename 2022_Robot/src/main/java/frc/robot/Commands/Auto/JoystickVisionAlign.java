package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.*;

public class JoystickVisionAlign extends CommandBase {

    private double tx;

    private double turnThrottle = 0;
    private double forwardThrottle = 0;
    private double currentAngularRate = 0;

    private double kPgain = 0.015; /* percent throttle per degree of error */
    private double kIgain = 0.001;
    private double kDgain = 0.0001; /* percent throttle per angular velocity dps */
    private double errorSum = 0;
    private double lastTimeStamp = 0;
    private double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimCamtran;
    NetworkTable table;
    NetworkTableInstance Inst;

    public void JoystickAutoAlign() {
        addRequirements(RobotContainer.drivetrain);
    }

    public void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        Timer.delay(0.25);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
        prelimtx = table.getEntry("tx");
        prelimCamtran = table.getEntry("camtran");
        errorSum = 0;
        lastTimeStamp = Timer.getFPGATimestamp();
    }
    public void execute() {
        forwardThrottle = Robot.robotContainer.getDriverLeftStickY();
        tx = prelimtx.getDouble(0);
        currentAngularRate = RobotContainer.drivetrain.getRoll();
        double dt = Timer.getFPGATimestamp() - lastTimeStamp;
        errorSum += tx * dt;

        turnThrottle = (tx) * kPgain + kIgain * errorSum - (currentAngularRate) * kDgain;
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);

        RobotContainer.drivetrain.drive(ControlMode.PercentOutput, left, right);

        lastTimeStamp = Timer.getFPGATimestamp();
    }

    public boolean isFinished() {
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
