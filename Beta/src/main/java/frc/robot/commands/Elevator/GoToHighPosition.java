package frc.robot.commands.Elevator;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GoToHighPosition extends CommandBase {

	private int conePosition = RobotContainer.elevator.getHighConePosition();
	private int cubePosition = RobotContainer.elevator.getHighCubePosition();

	private int safePosition = 50000;

	private boolean isCone;
	private boolean isTipped;
	
	private int desiredPosition;


	public GoToHighPosition() {
		addRequirements(RobotContainer.elevator);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		
		/* Check for game piece and whether elevator is tipped */
		isCone = RobotContainer.candleSubsystem.getIsCone();
		isTipped = RobotContainer.elevatorPiston.getIsTipped();

		/* Set desired height based on boolean values */
		if (isTipped == true) {
			if (isCone == true) {
				desiredPosition = conePosition;
			}
			else {
				desiredPosition = cubePosition;
			}
		}

		else {
			desiredPosition = safePosition;
		}	

		RobotContainer.elevator.setTargetPosition(desiredPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.elevator.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return RobotContainer.elevator.isInPosition(desiredPosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
