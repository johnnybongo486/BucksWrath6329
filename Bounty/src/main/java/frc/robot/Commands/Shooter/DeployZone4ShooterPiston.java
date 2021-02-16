package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DeployZone4ShooterPiston extends Command {

    public DeployZone4ShooterPiston() {
        requires(Robot.ShooterPiston);
    }

    protected void initialize() {
        Robot.ShooterPiston.zone4Deploy();
    }

    protected void execute() {
        
    }

   protected boolean isFinished() {
       return false;
   }

   protected void end() {
      
   }

   protected void interrupted(){

   }
}