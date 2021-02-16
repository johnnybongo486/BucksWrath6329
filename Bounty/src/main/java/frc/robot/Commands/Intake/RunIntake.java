package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RunIntake extends Command {

    public boolean beamBreak;

    public RunIntake() {
        requires(Robot.Intake);

    }

    protected void initialize () {

    }

    protected void execute () {
        Robot.Intake.runIntake();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }

    
}