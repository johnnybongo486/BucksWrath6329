package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class JoystickDrive extends CommandBase {

    public JoystickDrive() {
        addRequirements(Robot.drivetrain);
    }

    public void initialize() {
    }

    public void execute() {
        Robot.drivetrain.teleopDrive();
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
