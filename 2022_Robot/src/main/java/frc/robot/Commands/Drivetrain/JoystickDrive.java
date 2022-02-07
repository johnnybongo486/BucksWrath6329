package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class JoystickDrive extends CommandBase {
    private double turnValue = 0;
    private double moveValue = 0;

    public JoystickDrive() {
        addRequirements(RobotContainer.drivetrain);
    }

    public void initialize() {
    }

    @Override
    public void execute() {
        moveValue = Robot.robotContainer.getDriverLeftStickY();
        turnValue = -Robot.robotContainer.getDriverRightStickX();
        
        RobotContainer.drivetrain.teleopDrive(moveValue,turnValue);
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        SmartDashboard.putBoolean("Drive Command Interrupted", true);
    }
}
