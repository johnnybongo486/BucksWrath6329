package frc.robot.Commands.Elevator;

import frc.robot.*;
import edu.wpi.first.wpilibj.command.Command;

public class GoToRaiseRobotPosition extends Command {

	private int desiredPosition = Robot.LeftElevator.getRaiseRobotPosition();

	public GoToRaiseRobotPosition() {
		requires(Robot.LeftElevator);
		requires(Robot.RightElevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.LeftElevator.setTargetPosition(desiredPosition);
		Robot.RightElevator.setTargetPosition(desiredPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.LeftElevator.motionMagicControl();
		Robot.RightElevator.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.LeftElevator.isInPosition(desiredPosition) && Robot.RightElevator.isInPosition(desiredPosition);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}