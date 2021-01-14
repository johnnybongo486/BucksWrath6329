package frc.robot.Commands.Claw;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class CloseClaw extends Command {

    public CloseClaw() {
        requires(Robot.Claw);
    }

    protected void initialize() {
        Robot.Claw.Closed();
    }

    protected void execute () {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }


    protected void interrupted() {

    }
}