package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopShooter extends Command {
  
    private double zeroVelocity = Robot.Shooter.getZeroVelocity();

    public StopShooter() {
       requires(Robot.Shooter);
    }

    protected void initialize() {
        Robot.Shooter.setTargetVelocity(zeroVelocity);
        Robot.Shooter.ShooterFalcon.configClosedloopRamp(3);    
    }

    protected void execute() {
		Robot.Shooter.velocityControl();
    }

    protected boolean isFinished() {
        return Robot.Shooter.isAtVelocity(zeroVelocity);
    }

    protected void end() {
        Robot.Shooter.ShooterFalcon.configClosedloopRamp(0.5);
    }

    protected void interrupted() {

    }
}