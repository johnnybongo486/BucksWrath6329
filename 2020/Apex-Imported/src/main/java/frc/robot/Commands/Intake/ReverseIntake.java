package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ReverseIntake extends Command {
    public ReverseIntake() {
        requires(Robot.Intake);
    
    }

    protected void initialize () {

    }

    protected void execute () {
        Robot.Intake.reverseIntake();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }

    
}