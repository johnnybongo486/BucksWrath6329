package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class stopHopper extends Command {
   
    public stopHopper() {
    requires(Robot.Hopper);
    }

    protected void initialize() {
        Robot.Hopper.stopHopper();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}