package frc.robot.Commands.Claw;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class OpenClaw extends Command {

    public OpenClaw() {
        requires(Robot.Claw);
    }

    protected void initialize() {
        Robot.Claw.Open();
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