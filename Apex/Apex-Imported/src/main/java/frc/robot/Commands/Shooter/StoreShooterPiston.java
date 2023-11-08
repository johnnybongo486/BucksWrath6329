package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StoreShooterPiston extends Command {

    public StoreShooterPiston() {
        requires(Robot.ShooterPiston);
    }

    protected void initialize() {
        Robot.ShooterPiston.store();
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