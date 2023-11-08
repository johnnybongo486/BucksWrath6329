package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StoreIntakePiston extends Command {

    public StoreIntakePiston() {
        requires(Robot.IntakePiston);
    }

    protected void initialize() {
        Robot.IntakePiston.store();
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