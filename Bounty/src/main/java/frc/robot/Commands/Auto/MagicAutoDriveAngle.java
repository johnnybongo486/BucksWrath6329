package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MagicAutoDriveAngle extends Command {

     public double leftRotations = 0;
     public double rightRotations = 0;
     public double angle = 0;
     public double ticks = 0;
     public double error = 0;
     public double acceptableError = 100;

     public MagicAutoDriveAngle(double distance, double targetAngle) {
        requires(Robot.Drivetrain);
        
        this.ticks = distance * 6430;
        this.angle = targetAngle;
     }

     protected void initialize() {
          Robot.Drivetrain.resetDriveEncoders();
          
   }

   protected void execute() {
          Robot.Drivetrain.magicDriveAngle(ticks, angle);
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