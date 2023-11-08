package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ShooterPistonDeploy extends CommandBase {

    public ShooterPistonDeploy() {
        addRequirements(RobotContainer.shooterPiston);
    }

    public void initialize() {
        RobotContainer.shooterPiston.deploy();
    }

    public void execute() {

    }

   public boolean isFinished() {
       return false;
   }

   protected void end() {
      
   }

   protected void interrupted(){

   }
}
