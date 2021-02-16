package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopShooter extends Command {
  
    public StopShooter() {
       requires(Robot.Shooter);
    }

    protected void initialize() {
        Robot.Shooter.setTargetVelocity(5000);
        Robot.Shooter.ShooterFalcon.configClosedloopRamp(3);    
    }

    protected void execute() {
		Robot.Shooter.velocityControl();
    }

    protected boolean isFinished() {
        return Robot.Shooter.isAtVelocity(500);
    }

    protected void end() {
        Robot.Shooter.ShooterFalcon.configClosedloopRamp(1);
    }

    protected void interrupted() {

    }
}