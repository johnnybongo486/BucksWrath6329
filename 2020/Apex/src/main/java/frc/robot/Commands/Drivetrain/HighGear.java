package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HighGear extends Command {

    public HighGear() {
        requires(Robot.Shifter);
    }

    protected void initialize() {
        Robot.Shifter.highGear();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false; // || Robot.Drivetrain.getSpeed() < 10;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }
}