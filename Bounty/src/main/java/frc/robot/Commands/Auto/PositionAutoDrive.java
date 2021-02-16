package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PositionAutoDrive extends Command {

     public double leftTicks = 0;
     public double rightTicks = 0;
     public double leftError = 0;
     public double rightError = 0;
     public double acceptableError = 100;


     public PositionAutoDrive(double leftDistance, double rightDistance) {
        requires(Robot.Drivetrain);
        this.leftTicks = leftDistance * 6430;
        this.rightTicks = rightDistance * 6430;
     }

     protected void initialize() {
          Robot.Drivetrain.resetDriveEncoders();
          
   }

   protected void execute() {
          Robot.Drivetrain.smartDrive(leftTicks, rightTicks);
          leftError = Math.abs(leftTicks - Robot.Drivetrain.getLeftDistance());
          rightError = Math.abs(rightTicks - Robot.Drivetrain.getRightDistance());
          Robot.Drivetrain.setTargetDistance(leftTicks);
          Robot.Drivetrain.setDistanceError(leftTicks - Robot.Drivetrain.getLeftDistance());
   }

   protected boolean isFinished() {
          return leftError < acceptableError && rightError < acceptableError;
   }

   protected void end() {
        Robot.Drivetrain.stopDrivetrain();
   }

   protected void interrupted(){
        end();
   }
  
}