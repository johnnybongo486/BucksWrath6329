package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MagicAutoDrive extends CommandBase {

     public double leftRotations = 0;
     public double rightRotations = 0;
     public double angle = 0;
     public double ticks = 0;
     public double error = 0;
     public double acceptableError = 100;

     public MagicAutoDrive(double distance) {
        addRequirements(RobotContainer.drivetrain);
        
        this.ticks = distance * 13266;  //2046*Gearbox / 1.5708 // 10360 on practice bot
     }

     public void initialize() {
          RobotContainer.drivetrain.resetDriveEncoders();
          
   }

   public void execute() {
          RobotContainer.drivetrain.magicDrive(-ticks);
          RobotContainer.drivetrain.setTargetDistance(-ticks);
          error = Math.abs(ticks - RobotContainer.drivetrain.getRightDistance());
          RobotContainer.drivetrain.setDistanceError(error);

   }

   public boolean isFinished() {
          return error < acceptableError;
   }

   protected void end() {
        RobotContainer.drivetrain.stopDrivetrain();
   }

   protected void interrupted(){
        end();
   }
  
}
