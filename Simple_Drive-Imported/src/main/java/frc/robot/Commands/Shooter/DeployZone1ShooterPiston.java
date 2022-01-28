package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DeployZone1ShooterPiston extends CommandBase {

    public DeployZone1ShooterPiston() {
        addRequirements(RobotContainer.shooterPiston);
    }

    public void initialize() {
        RobotContainer.shooterPiston.zone1Deploy();
    }

    public void execute() {
        RobotContainer.shooterPiston.zone1Deploy();
    }

   public boolean isFinished() {
       return false;
   }

   protected void end() {
      
   }

   protected void interrupted(){

   }
}
