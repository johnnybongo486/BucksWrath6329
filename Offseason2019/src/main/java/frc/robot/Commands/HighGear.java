package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class HighGear extends Command {

    public HighGear() {
        requires(Robot.Shifter);
    }

    protected void initialize() {
        Robot.Shifter.HighGear();
    }

    protected void execute () {

    }

    protected boolean isFinished() {
        return false || Robot.Drivetrain.getLeftSpeed() < 10;
    }

    protected void end() {

    }


    protected void interrupted() {

    }
}