package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RumbleLeft extends Command {

    public RumbleLeft() {

    }

    protected void initialize() {
        setTimeout(1);
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
        end();
    }
}