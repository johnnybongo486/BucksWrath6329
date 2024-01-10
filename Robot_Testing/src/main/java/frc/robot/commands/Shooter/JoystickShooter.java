package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class JoystickShooter extends Command {

	private int positionIncrement = 100;
    
    public JoystickShooter() {
        addRequirements(RobotContainer.leftShooter);
		addRequirements(RobotContainer.rightShooter);
    }
	// Called just before this Command runs the first time
	public void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {

		// joystick control
        double signal = RobotContainer.rightShooter.joystickShooter();
		double uSignal = RobotContainer.leftShooter.joystickShooter();
        
        RobotContainer.rightShooter.incrementTargetVelocity((int) (signal * positionIncrement));
        RobotContainer.leftShooter.incrementTargetVelocity((int) (uSignal * positionIncrement));

		RobotContainer.rightShooter.velocityControl();
		RobotContainer.leftShooter.velocityControl();
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
