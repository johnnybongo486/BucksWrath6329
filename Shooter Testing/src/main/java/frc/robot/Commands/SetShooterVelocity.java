package frc.robot.Commands;

import frc.robot.*;
import edu.wpi.first.wpilibj.command.Command;

public class SetShooterVelocity extends Command {
	private double shotVelocity = 0;
	private double runTime = 3;

	public SetShooterVelocity(double velocity) {
		this.shotVelocity = velocity;
		requires(Robot.Shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.Shooter.setTargetVelocity(shotVelocity);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.Shooter.velocityControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
			return Robot.Shooter.isAtVelocity(shotVelocity) || this.timeSinceInitialized() >= runTime;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}