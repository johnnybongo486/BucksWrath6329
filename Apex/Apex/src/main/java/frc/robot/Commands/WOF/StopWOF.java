package frc.robot.Commands.WOF;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopWOF extends Command {

   public StopWOF() {
          requires(Robot.WOF);
          
   }

   protected void initialize() {

   }

   protected void execute() {
          Robot.WOF.stopWOF();
   }

   protected boolean isFinished() {
        return false;
   }

   protected void end() {

    }

   protected void interrupted(){
        end();
   }
}

