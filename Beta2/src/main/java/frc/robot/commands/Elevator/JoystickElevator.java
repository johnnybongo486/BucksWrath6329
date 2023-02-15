package frc.robot.commands.Elevator;

import frc.robot.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickElevator extends CommandBase {

	private int positionIncrement = 10000;

	public JoystickElevator() {
		addRequirements(RobotContainer.elevator);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {

		// joystick control
		double elevatorSignal = -RobotContainer.elevator.JoyStickElevator();
        
		RobotContainer.elevator.incrementTargetPosition((int) (elevatorSignal * positionIncrement));

		RobotContainer.elevator.motionMagicControl();
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