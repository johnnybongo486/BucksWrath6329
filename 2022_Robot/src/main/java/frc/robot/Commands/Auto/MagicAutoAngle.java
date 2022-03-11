package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MagicAutoAngle extends CommandBase {

     public double leftRotations = 0;
     public double rightRotations = 0;
     public double angle = 0;
     public double ticks = 0;
     public double errorRight = 0;
     public double errorLeft = 0;
     public double acceptableError = 500;
     public double kP = 0;

     public MagicAutoAngle(double angle, double p) {
        addRequirements(RobotContainer.drivetrain);
        this.kP = p;
        this.ticks = angle * 275;  //2046*Gearbox / 1.5708 // 10360 on practice bot
     }

     public void initialize() {
          RobotContainer.drivetrain.resetDriveEncoders();
          RobotContainer.drivetrain.rightConfig.slot0.kP = kP;
          RobotContainer.drivetrain.leftConfig.slot0.kP = kP;
          RobotContainer.drivetrain.rightLead.configAllSettings(RobotContainer.drivetrain.rightConfig);
          RobotContainer.drivetrain.rightFollower.configAllSettings(RobotContainer.drivetrain.rightConfig);
          RobotContainer.drivetrain.leftLead.configAllSettings(RobotContainer.drivetrain.leftConfig); 
          RobotContainer.drivetrain.leftFollower.configAllSettings(RobotContainer.drivetrain.leftConfig); 

   }

   public void execute() {
          RobotContainer.drivetrain.magicTurn(-ticks, ticks);
          RobotContainer.drivetrain.setTargetDistance(ticks);
          errorRight = Math.abs(ticks - RobotContainer.drivetrain.getRightDistance());
          errorLeft = Math.abs(ticks - RobotContainer.drivetrain.getLeftDistance());
          RobotContainer.drivetrain.setDistanceError(errorRight);

   }

   public boolean isFinished() {
          return errorRight < acceptableError;
   }

   protected void end() {
     RobotContainer.drivetrain.stopDrivetrain();
     RobotContainer.drivetrain.resetDriveEncoders();
   }

   protected void interrupted(){
        end();
   }
  
}
