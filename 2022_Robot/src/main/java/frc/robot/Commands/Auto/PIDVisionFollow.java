package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.*;

public class PIDVisionFollow extends CommandBase {

    private double tx;
    private double ta;

    private double turnThrottle = 0;
    private double forwardThrottle = 0;
    private double currentAngularRate = 0;

    // turn PID
    private double kPgain = 0.02; /* percent throttle per degree of error */ 
    private double kIgain = 0.0;
    private double kDgain = 0.000; /* percent throttle per angular velocity dps */

    // speed PID
    private double dPgain = .65; /* percent throttle per degree of error */ 
    private double dIgain = 0.0;
    private double dDgain = 0.0; /* percent throttle per angular velocity dps */

    // Math
    private double kErrorSum = 0;
    private double dErrorSum = 0;
    private double errorRate = 0 ;
    private double lastTimeStamp = 0;
    private double lastError = 0;
    private double taError = 0;
    private double targetta = 1.0;  // needs to be found
    private double kMaxCorrectionRatio = 0.150; /* cap corrective turning throttle to 30 percent of forward throttle */

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimta;
    NetworkTableEntry prelimCamtran;
    NetworkTable table;
    NetworkTableInstance Inst;

    public PIDVisionFollow() {
        addRequirements(RobotContainer.drivetrain);
    }

    public void initialize() {
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
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

    public void execute() {
        tx = prelimtx.getDouble(0);
        ta = prelimta.getDouble(0);
        currentAngularRate = RobotContainer.drivetrain.getRoll();
        double dt = Timer.getFPGATimestamp() - lastTimeStamp;
        taError = targetta - ta;
        errorRate = (taError - lastError) / dt;
        kErrorSum += tx * dt;
        dErrorSum += taError * dt;

        forwardThrottle = taError *dPgain + dIgain * dErrorSum + errorRate *dDgain;
        turnThrottle = tx * kPgain + kIgain * kErrorSum + currentAngularRate * kDgain;
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = -forwardThrottle - turnThrottle;
        double right = -forwardThrottle + turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);

        RobotContainer.drivetrain.drive(ControlMode.PercentOutput, left, left, left, right, right, right);

        lastTimeStamp = Timer.getFPGATimestamp();
        lastError = taError;
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {

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
