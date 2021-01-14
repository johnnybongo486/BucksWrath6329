package frc.robot.Commands.Wrist;

import frc.robot.*;
import edu.wpi.first.wpilibj.command.Command;

public class SetBooleanFalse extends Command {

	public SetBooleanFalse() {
		requires(Robot.Wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.Wrist.setIsHatchMode(false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

    }

	// Make this return False when this Command no longer needs to run execute()
	protected boolean isFinished() {
			return false;
	}

	// Called once after isFinished returns False
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}