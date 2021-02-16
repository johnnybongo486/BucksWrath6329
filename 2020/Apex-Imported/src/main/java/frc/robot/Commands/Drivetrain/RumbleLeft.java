package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RumbleLeft extends Command {

    public RumbleLeft() {

    }

    protected void initialize() {
        setTimeout(0.5);
    }

    protected void execute() {
        Robot.oi.rumbleLeft();
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        Robot.oi.stopRumble();
    }

    protected void interrupted() {
        
    }
}