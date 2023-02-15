package frc.robot.commands.Wrist;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GoToFloorIntakePosition extends CommandBase {

	private int conePosition = RobotContainer.wrist.getFloorConePosition();
	private int cubePosition = RobotContainer.wrist.getFloorCubePosition();
	private boolean isCone;
	
	private int desiredPosition;


	public GoToFloorIntakePosition() {
		addRequirements(RobotContainer.wrist);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		isCone = RobotContainer.candleSubsystem.getIsCone();
		if (isCone == true) {
			desiredPosition = conePosition;
		}
		else {
			desiredPosition = cubePosition;
		}
		RobotContainer.wrist.setTargetPosition(desiredPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		RobotContainer.wrist.motionMagicControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return RobotContainer.wrist.isInPosition(desiredPosition);
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
