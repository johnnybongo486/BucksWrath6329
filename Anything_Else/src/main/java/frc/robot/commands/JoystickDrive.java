package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class JoystickDrive extends CommandBase {

    public JoystickDrive() {
        addRequirements(RobotContainer.drivetrain);
    }

    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.drivetrain.teleopDrive();
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
