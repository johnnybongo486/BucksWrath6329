package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class IntakeHatch extends Command {

    public IntakeHatch() {
        requires(Robot.Intake);
    }

    protected void initialize() {
        Robot.Intake.intakeHatch();
    }

    protected void execute () {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.Intake.stopIntake();
    }


    protected void interrupted() {

    }
}