package frc.robot.Commands.Climber;

import frc.robot.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickClimber extends CommandBase {

	private int LpositionIncrement = 200;
	private int RpositionIncrement = 200;

	public JoystickClimber() {
		addRequirements(RobotContainer.leftClimber);
		addRequirements(RobotContainer.rightClimber);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {

		// joystick control
		double leftSignal = -RobotContainer.leftClimber.leftJoyStickClimber();
		double rightSignal = -RobotContainer.rightClimber.rightJoyStickClimber();
        
		RobotContainer.leftClimber.incrementTargetPosition((int) (leftSignal * LpositionIncrement));
		RobotContainer.rightClimber.incrementTargetPosition((int) (rightSignal * RpositionIncrement));

		RobotContainer.leftClimber.motionMagicControl();
		RobotContainer.rightClimber.motionMagicControl();
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
