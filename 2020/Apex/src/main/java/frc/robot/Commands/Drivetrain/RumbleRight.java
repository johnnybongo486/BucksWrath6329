package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RumbleRight extends Command {

    public RumbleRight() {

    }

    protected void initialize() {
        setTimeout(0.5);
    }

    protected void execute() {
        Robot.oi.rumbleRight();
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