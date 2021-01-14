package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DeployShooterPiston extends Command {

    public DeployShooterPiston() {
        requires(Robot.ShooterPiston);
    }

    protected void initialize() {
        Robot.ShooterPiston.deploy();
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