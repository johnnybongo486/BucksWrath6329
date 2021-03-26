package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MagicAutoDrive extends Command {

     public double leftRotations = 0;
     public double rightRotations = 0;
     public double angle = 0;
     public double ticks = 0;
     public double error = 0;
     public double acceptableError = 100;

     public MagicAutoDrive(double distance) {
        requires(Robot.Drivetrain);
        
        this.ticks = distance * 12860;
     }

     protected void initialize() {
          Robot.Drivetrain.resetDriveEncoders();
          
   }

   protected void execute() {
          Robot.Drivetrain.magicDrive(ticks);
          Robot.Drivetrain.setTargetDistance(ticks);
          error = Math.abs(ticks - Robot.Drivetrain.getRightDistance());
          Robot.Drivetrain.setDistanceError(error);

   }

   protected boolean isFinished() {
          return error < acceptableError;
   }

   protected void end() {
        Robot.Drivetrain.stopDrivetrain();
   }

   protected void interrupted(){
        end();
   }
  
}