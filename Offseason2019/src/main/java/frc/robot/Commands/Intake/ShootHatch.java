package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class ShootHatch extends Command {

    public ShootHatch() {
        requires(Robot.Intake);
    }

    protected void initialize() {
        Robot.Intake.scoreHatch();
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