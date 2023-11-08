package frc.robot.commands.Shooter;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetUpperShooterVelocity extends CommandBase {
	private double shotVelocity = 0;

	public SetUpperShooterVelocity(double velocity) {
		this.shotVelocity = velocity;
		addRequirements(RobotContainer.upperShooter);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.upperShooter.setTargetVelocity(shotVelocity);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.upperShooter.velocityControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
			return RobotContainer.upperShooter.isAtVelocity(shotVelocity);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
