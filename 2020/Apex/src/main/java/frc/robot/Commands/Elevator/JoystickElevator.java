package frc.robot.Commands.Elevator;

import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

public class JoystickElevator extends Command {

	private int RpositionIncrement = 100;
	private int LpositionIncrement = 50;

	public JoystickElevator() {
		requires(Robot.LeftElevator);
		requires(Robot.RightElevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// joystick control
		double leftSignal = Robot.oi.getOperatorRightStickY();
		double rightSignal = -Robot.oi.getOperatorLeftStickY();
        
		Robot.LeftElevator.incrementTargetPosition((int) (leftSignal * LpositionIncrement));
		Robot.RightElevator.incrementTargetPosition((int) (rightSignal * RpositionIncrement));

		Robot.LeftElevator.motionMagicControl();
		Robot.RightElevator.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}