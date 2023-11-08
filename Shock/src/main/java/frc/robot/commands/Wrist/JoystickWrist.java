package frc.robot.commands.Wrist;

import frc.robot.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickWrist extends CommandBase {

	private int positionIncrement = 2000;

	public JoystickWrist() {
		addRequirements(RobotContainer.wrist);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {

		// joystick control
		double wristSignal = -RobotContainer.wrist.JoyStickWrist();
        
		RobotContainer.wrist.incrementTargetPosition((int) (wristSignal * positionIncrement));

		RobotContainer.wrist.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
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