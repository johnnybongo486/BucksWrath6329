package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DeployZone2ShooterPiston extends Command {

    public DeployZone2ShooterPiston() {
        requires(Robot.ShooterPiston);
    }

    protected void initialize() {
        Robot.ShooterPiston.zone2Deploy();
    }

    protected void execute() {
        Robot.ShooterPiston.zone2Deploy();
    }

   protected boolean isFinished() {
       return false;
   }

   protected void end() {
      
   }

   protected void interrupted(){

   }
}