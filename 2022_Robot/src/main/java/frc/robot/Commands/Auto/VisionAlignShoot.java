package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.opencv.core.Mat;

import edu.wpi.first.networktables.*;

public class VisionAlignShoot extends CommandBase {

    private double tx;
    private double ty;

    private double turnThrottle = 0;
    private double forwardThrottle = 0;
    //private double currentAngularRate = 0;

    private double shooterVelocity;
    private double upperVelocity;

    private double kPgain = 0.016; /* percent throttle per degree of error */   // 0.016 
    private double kIgain = 0.2;  // .06
    private double kDgain = 0.0002; /* percent throttle per angular velocity dps */
    private double errorSum = 0;
    private double iLimit = 4;
    private double lastTimeStamp = 0;
    private double lastError = 0; 
    private double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimty;
    NetworkTableEntry prelimCamtran;
    NetworkTable table;
    NetworkTableInstance Inst;

    public VisionAlignShoot() {
        addRequirements(RobotContainer.drivetrain);
        addRequirements(RobotContainer.shooter);
        addRequirements(RobotContainer.upperShooter);
    }

    public void initialize() {
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
        prelimtx = table.getEntry("tx");
        prelimty = table.getEntry("ty");
        prelimCamtran = table.getEntry("camtran");
        errorSum = 0;
        lastError = 0;
        lastTimeStamp = Timer.getFPGATimestamp();
        ty = prelimty.getDouble(0);
    }
    public void execute() {
        forwardThrottle = -Robot.robotContainer.getDriverLeftStickY();
        tx = prelimtx.getDouble(0);
        ty = prelimty.getDouble(0);

        //shooterVelocity = (4.39*ty*ty) - (152.64*ty) + 7387.31;  // 7387.31
        //shooterVelocity = (5.1256*ty*ty) - (152.7151*ty) + 7355;
        shooterVelocity = (6.8741*ty*ty) - (131.4174*ty) + 6880;  // 7238
        upperVelocity = shooterVelocity*1.5;

        RobotContainer.shooter.setTargetVelocity(shooterVelocity);
        RobotContainer.upperShooter.setTargetVelocity(upperVelocity);

        //currentAngularRate = RobotContainer.drivetrain.getRoll();
        double dt = Timer.getFPGATimestamp() - lastTimeStamp;
       
        if (Math.abs(tx) < iLimit) {
            errorSum += tx * dt;
        }

        double errorRate = (tx - lastError) / dt;

        turnThrottle = (tx) * kPgain + kIgain * errorSum + errorRate * kDgain;
        double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
        turnThrottle = Cap(turnThrottle, maxThrot);
        double left = forwardThrottle + turnThrottle;
        double right = forwardThrottle - turnThrottle;
        left = Cap(left, 1.0);
        right = Cap(right, 1.0);

        RobotContainer.drivetrain.drive(ControlMode.PercentOutput, left, left, left, right, right, right);
        RobotContainer.shooter.velocityControl();
        RobotContainer.upperShooter.velocityControl();

        lastTimeStamp = Timer.getFPGATimestamp();
        lastError = tx;

        if (Math.abs(tx) < 1.5 && Math.abs(tx) != 0) {
            System.out.println("SHOOT HARRY!");
        }
    }

    public boolean isFinished() {
        return false; //&& RobotContainer.shooter.isAtVelocity(shooterVelocity) && RobotContainer.upperShooter.isAtVelocity(upperVelocity);
    }

    protected void end() {
        RobotContainer.drivetrain.stopDrivetrain();
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
        if(forwardThrot < 1)    // was 0.3
            return 1;           // was 0.3
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
