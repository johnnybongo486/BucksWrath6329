package frc.robot.Commands.Shooter;

import frc.robot.*;
import edu.wpi.first.wpilibj.command.Command;

public class SetCloseShotVelocity extends Command {
	private double closeShotVelocity = Robot.Shooter.getCloseShotVelocity();

	public SetCloseShotVelocity() {
		requires(Robot.Shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.Shooter.setTargetVelocity(closeShotVelocity);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.Shooter.velocityControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
			return Robot.Shooter.isAtVelocity(closeShotVelocity);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}