package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class HoldCargo extends Command {

    public HoldCargo() {
        requires(Robot.Intake);
    }

    protected void initialize() {
        Robot.Intake.holdCargo();
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