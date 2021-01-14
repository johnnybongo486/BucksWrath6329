package frc.robot.Commands.Wrist;

import frc.robot.*;
import edu.wpi.first.wpilibj.command.Command;

public class GoToIntakePosition extends Command {

	private int intakePosition = Robot.Wrist.getrunIntakePosition();

	public GoToIntakePosition() {
		requires(Robot.Wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.Wrist.setTargetPosition(intakePosition);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.Wrist.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
			return Robot.Wrist.isInPosition(intakePosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}