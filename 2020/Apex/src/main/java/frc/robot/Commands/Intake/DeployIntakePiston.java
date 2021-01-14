package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DeployIntakePiston extends Command {

    public DeployIntakePiston() {
        requires(Robot.IntakePiston);
    }

    protected void initialize() {
        Robot.IntakePiston.deploy();
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