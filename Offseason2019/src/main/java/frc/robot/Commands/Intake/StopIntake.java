package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class StopIntake extends Command {

    public StopIntake() {
        requires(Robot.Intake);
    }

    protected void initialize() {
        Robot.Intake.stopIntake();
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