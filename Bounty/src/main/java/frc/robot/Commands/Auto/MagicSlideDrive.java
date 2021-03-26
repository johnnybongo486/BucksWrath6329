package frc.robot.Commands.Auto;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MagicSlideDrive extends Command {

     public double ticks = 0;
     public double error = 0;
     public double acceptableError = 100;
     private double targetAngle = 0;
     private double currentAngle = 0;
     private double currentAngularRate = 0;
     private double turnThrottle = 0;
     private double forwardThrottle = 0;
     private double rotateGain = 0;
     private double kPgain = 0.01; /* percent throttle per degree of error */ //was .04
     private double kDgain = 0.004; /* percent throttle per angular velocity dps */
     private double kMaxCorrectionRatio = 0.30; /* cap corrective turning throttle to 30 percent of forward throttle */
     public double kPgainSpeed = 0.1;
     public double kIgainSpeed = 0;   //0.000005
     public double kDgainSpeed = 0;  // 0.005
     public double kIZone = 12;
     public double driveDistance = 0;
     public double currentDistance = 0;
     public double maxSpeed = 0;
     public double distanceAcceptableError = 1000;
     public double AVGerror = 0;
     public double errorSum = 0;
     public double lastTimeStamp = 0;
     public double dt = 0;
     public double absError = 0;
     public double errorRate = 0;
     public double lastError = 0;
     public double distanceError = 0;


     public MagicSlideDrive(double distance, double targetDistance, double maxSpeed) {
        requires(Robot.SlideDrive);
        requires(Robot.Drivetrain);
        this.ticks = distance * 15825;
        this.driveDistance = targetDistance * 12860;   
        this.maxSpeed = maxSpeed;
     }

     protected void initialize() {
          Robot.SlideDrive.resetDriveEncoders();
          Robot.Drivetrain.resetDriveEncoders();
          targetAngle = Robot.Drivetrain.getAngle();
          errorSum = 0;
          distanceError = 0;
          absError = 0;
          errorRate = 0;
          lastError = 0;
          lastTimeStamp = Timer.getFPGATimestamp();
     }

     protected void execute() {
          Robot.SlideDrive.magicDrive(ticks);
          currentDistance = Robot.Drivetrain.getRightDistance();
          currentAngle = Robot.Drivetrain.getAngle();
          currentAngularRate = Robot.Drivetrain.getRoll();
          
          // change in time
          dt = Timer.getFPGATimestamp() - lastTimeStamp;

          // Calculate P
          distanceError = driveDistance - currentDistance;
          absError = Math.abs(driveDistance - currentDistance);  // for when to finish
          error = Math.abs(ticks - Robot.SlideDrive.getSlideDistance());  // slide when to finish

          // Calculate I if in IZone
          if (Math.abs(distanceError) < kIZone) {
               errorSum += distanceError * dt;
          }

          // Calculate D
          errorRate = (distanceError - lastError) / dt;

          // Calculate Forward Throttle with PID
          forwardThrottle = distanceError * kPgainSpeed + errorSum * kIgainSpeed + errorRate * kDgainSpeed;
          turnThrottle = (targetAngle - currentAngle) * kPgain - (currentAngularRate) * kDgain;  // should this be added?

          double maxThrot = MaxCorrection(forwardThrottle, kMaxCorrectionRatio);
          turnThrottle = Cap(turnThrottle, maxThrot);
          double left = forwardThrottle - turnThrottle;
          double right = forwardThrottle + turnThrottle;
          left = Cap(left, maxSpeed);
          right = Cap(right, maxSpeed);

          //drive
          Robot.Drivetrain.drive(ControlMode.PercentOutput, left, right);

          // Update Variables to Current
          lastTimeStamp = Timer.getFPGATimestamp();
          lastError = distanceError;
     }

     protected boolean isFinished() {
          return error < acceptableError && distanceError < distanceAcceptableError;
     }

     protected void end() {
        Robot.Drivetrain.stopDrivetrain();
        Robot.SlideDrive.stopSlide();
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
  