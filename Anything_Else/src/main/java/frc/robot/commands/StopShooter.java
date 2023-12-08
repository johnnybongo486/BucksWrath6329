package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class StopShooter extends CommandBase {
  
    public StopShooter() {
        addRequirements(RobotContainer.shooter);
        addRequirements(RobotContainer.upperShooter);
    }

    public void initialize() {
        RobotContainer.shooter.setTargetVelocity(0);
        RobotContainer.upperShooter.setTargetVelocity(0);
        RobotContainer.shooter.ShooterFalcon.configClosedloopRamp(3);    
        RobotContainer.upperShooter.UpperShooterFalcon.configClosedloopRamp(3);    

    }

    public void execute() {
		RobotContainer.shooter.velocityControl();
        RobotContainer.upperShooter.velocityControl();

    }

    public boolean isFinished() {
        return RobotContainer.shooter.isAtVelocity(0) && RobotContainer.upperShooter.isAtVelocity(0);
    }

    protected void end() {
        RobotContainer.shooter.ShooterFalcon.configClosedloopRamp(1);
        RobotContainer.upperShooter.UpperShooterFalcon.configClosedloopRamp(1);
    }

    protected void interrupted() {

    }
}
