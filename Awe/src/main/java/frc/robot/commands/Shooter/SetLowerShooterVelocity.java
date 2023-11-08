package frc.robot.commands.Shooter;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetLowerShooterVelocity extends CommandBase {
	private double shotVelocity = 0;

	public SetLowerShooterVelocity(double velocity) {
		this.shotVelocity = velocity;
		addRequirements(RobotContainer.lowerShooter);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.lowerShooter.setTargetVelocity(shotVelocity);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.lowerShooter.velocityControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
			return RobotContainer.lowerShooter.isAtVelocity(shotVelocity);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
