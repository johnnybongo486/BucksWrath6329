package frc.robot.Commands.Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Models.*;

public class JoystickDrive extends Command {

    DriveHelper helper;

    private double quickTurnThreshold = 0.2;
    private double turnThrottle = 0;
    private double forwardThrottle = 0;
    private double rotateGain = 0;
    private double kPgain = 0.01; /* percent throttle per degree of error */ //was .04
    private double kDgain = 0.004; /* percent throttle per angular velocity dps */
    private double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */
    private int _printLoops = 0;
    private double targetAngle = 0;
    private double currentAngle = 0;
    private double currentAngularRate = 0;
    
     
    public JoystickDrive() {
       requires(Robot.Drivetrain);
       helper = new DriveHelper();
    }

    protected void initialize() {

    }

    protected void execute() {
        // Get Joystick Values
        double moveValue = Robot.oi.getDriverLeftStickY();
        double rotateValue = Robot.oi.getDriverRightStickX();

        // H Drive
        if (Robot.SlideDrive.isSlideActive() == true) {

            currentAngle = Robot.Drivetrain.getAngle();
            currentAngularRate = Robot.Drivetrain.getRoll();

            forwardThrottle = -moveValue;

            // add these to rotate while strafing
            rotateGain = -rotateValue; 
            targetAngle = targetAngle + rotateGain*4;  // this needs to be changed to improve turn times

            turnThrottle = (targetAngle - currentAngle) * kPgain - (currentAngularRate) * kDgain;  // should this be added?
			/* the max correction is the forward throttle times a scalar,
			 * This can be done a number of ways but basically only apply small turning correction when we are moving slow
			 * and larger correction the faster we move.  Otherwise you may need stiffer pgain at higher velocities. */
		    double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
            turnThrottle = Cap(turnThrottle, maxThrot);
            double left = forwardThrottle - turnThrottle;
            double right = forwardThrottle + turnThrottle;
            left = Cap(left, 1.0);
            right = Cap(right, 1.0);
            Robot.Drivetrain.drive(ControlMode.PercentOutput, left, right);
        }

        else {
            // New Thing
            targetAngle = Robot.Drivetrain.getAngle();
            rotateValue = rotateValue / 2;  // needs to be tuned
            boolean quickTurn = (moveValue < quickTurnThreshold && moveValue > -quickTurnThreshold);
            DriveSignal driveSignal = helper.cheesyDrive(-moveValue, rotateValue, quickTurn, false);
            Robot.Drivetrain.drive(ControlMode.PercentOutput, driveSignal);
        }

        if (++_printLoops > 50){
			_printLoops = 0;
			
			System.out.println("------------------------------------------");
            System.out.println("error: " + (targetAngle - currentAngle) );
            System.out.println("target angle: "+ targetAngle);
			System.out.println("angle: "+ currentAngle);
			System.out.println("rate: "+ currentAngularRate);
			System.out.println("------------------------------------------");
		}
    
   }

   protected boolean isFinished() {
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
