package frc.robot.Commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class JoystickWrist extends Command {

	private int positionIncrement = 20;

	public JoystickWrist() {
		requires(Robot.Wrist);

	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// joystick control
		double signal = -Robot.oi.getOperatorLeftStickY();
		Robot.Wrist.incrementTargetPosition((int) (signal * positionIncrement));
		Robot.Wrist.motionMagicControl();

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