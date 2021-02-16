package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DeployZone1ShooterPiston extends Command {

    public DeployZone1ShooterPiston() {
        requires(Robot.ShooterPiston);
    }

    protected void initialize() {
        Robot.ShooterPiston.zone1Deploy();
    }

    protected void execute() {
        Robot.ShooterPiston.zone1Deploy();
    }

   protected boolean isFinished() {
       return false;
   }

   protected void end() {
      
   }

   protected void interrupted(){

   }
}