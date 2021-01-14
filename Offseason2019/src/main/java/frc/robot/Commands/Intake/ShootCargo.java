package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class ShootCargo extends Command {

    public ShootCargo() {
        requires(Robot.Intake);
    }

    protected void initialize() {
        Robot.Intake.scoreCargo();
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