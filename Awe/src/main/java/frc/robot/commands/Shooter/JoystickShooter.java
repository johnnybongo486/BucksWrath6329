package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class JoystickShooter extends CommandBase {

	private int positionIncrement = 100;
    
    public JoystickShooter() {
        addRequirements(RobotContainer.lowerShooter);
		addRequirements(RobotContainer.upperShooter);
    }
	// Called just before this Command runs the first time
	public void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {

		// joystick control
        double signal = RobotContainer.lowerShooter.joystickShooter();
		double uSignal = RobotContainer.upperShooter.joystickShooter();
        
        RobotContainer.lowerShooter.incrementTargetVelocity((int) (signal * positionIncrement));
        RobotContainer.upperShooter.incrementTargetVelocity((int) (uSignal * positionIncrement));

		RobotContainer.lowerShooter.velocityControl();
		RobotContainer.upperShooter.velocityControl();
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
