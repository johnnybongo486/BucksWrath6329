package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class HoldHatch extends Command {

    public HoldHatch() {
        requires(Robot.Intake);
    }

    protected void initialize() {
        Robot.Intake.holdHAtch();
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