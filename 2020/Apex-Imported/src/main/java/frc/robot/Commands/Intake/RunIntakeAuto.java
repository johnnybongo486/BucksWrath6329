package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RunIntakeAuto extends Command {

    private double runTime = 0;

    public RunIntakeAuto(double timeout) {
        requires(Robot.Intake);
        this.runTime = timeout;

    }

    protected void initialize () {

    }

    protected void execute () {
        Robot.Intake.runIntake();
    }

    protected boolean isFinished() {
        return (this.timeSinceInitialized() >= runTime);
    }

    protected void end() {
        Robot.Intake.stopIntake();
    }

    protected void interrupted() {
        end();
    }

    
}