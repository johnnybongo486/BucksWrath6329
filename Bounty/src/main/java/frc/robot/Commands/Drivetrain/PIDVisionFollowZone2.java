package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.*;

public class PIDVisionFollowZone2 extends Command {

    private double tx;
    private double ta;
    private double[] camtran = new double[6];
    private double angle;

    private double turnThrottle = 0;
    private double forwardThrottle = 0;
    private double currentAngularRate = 0;

    // turn PID
    private double kPgain = 0.01; /* percent throttle per degree of error */ //was .04
    private double kIgain = 0.0;
    private double kDgain = 0.0001; /* percent throttle per angular velocity dps */

    // speed PID
    private double dPgain = 0.5; /* percent throttle per degree of error */ //was .04
    private double dIgain = 0.0;
    private double dDgain = 0.000001; /* percent throttle per angular velocity dps */

    // Math
    private double kErrorSum = 0;
    private double dErrorSum = 0;
    private double errorRate = 0 ;
    private double lastTimeStamp = 0;
    private double lastError = 0;
    private double taError = 0;
    private double targetta = 1.95;  // needs to be found
    private double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimta;
    NetworkTableEntry prelimCamtran;
    NetworkTable table;
    NetworkTableInstance Inst;

    public PIDVisionFollowZone2() {
        requires(Robot.Drivetrain);
    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("camMode").setNumber(0);
        Timer.delay(0.25);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight-shooter");
        prelimtx = table.getEntry("tx");
        prelimta = table.getEntry("ta");

        prelimCamtran = table.getEntry("camtran");
        kErrorSum = 0;
        dErrorSum = 0;
        errorRate = 0;
        lastTimeStamp = Timer.getFPGATimestamp();
        lastError = 0;
        taError = 0;
    }

    protected void execute() {
        tx = prelimtx.getDouble(0);
        ta = prelimta.getDouble(0);
        angle = camtran[1];
        currentAngularRate = Robot.Drivetrain.getRoll();
        double dt = Timer.getFPGATimestamp() - lastTimeStamp;
        taError = targetta - ta;
        errorRate = (taError - lastError) / dt;
        kErrorSum += tx * dt;
        dErrorSum += taError * dt;

        forwardThrottle = taError *dPgain + dIgain * dErrorSum + errorRate *dDgain;
        turnThrottle = tx * kPgain + kIgain * kErrorSum + currentAngularRate * kDgain;
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);

        if (Math.abs(taError) >= 0.05) {
            Robot.Drivetrain.drive(ControlMode.PercentOutput, left, right);
        }

        else {
            Robot.Drivetrain.drive(ControlMode.PercentOutput, left/4, right/4);
        }

        lastTimeStamp = Timer.getFPGATimestamp();
        lastError = taError;
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