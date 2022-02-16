package frc.robot.Commands.Climber;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GoToClimbPosition extends CommandBase {

	private int ldesiredPosition = RobotContainer.leftClimber.getClimbPosition();
	private int rdesiredPosition = RobotContainer.rightClimber.getClimbPosition();


	public GoToClimbPosition() {
		addRequirements(RobotContainer.leftClimber);
		addRequirements(RobotContainer.rightClimber);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.leftClimber.setTargetPosition(ldesiredPosition);
		RobotContainer.rightClimber.setTargetPosition(rdesiredPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.leftClimber.motionMagicControl();
		RobotContainer.rightClimber.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return RobotContainer.leftClimber.isInPosition(ldesiredPosition) && RobotContainer.rightClimber.isInPosition(rdesiredPosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}