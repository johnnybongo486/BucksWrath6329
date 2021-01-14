package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class IntakeStop extends Command{
    public IntakeStop(){
        requires(Robot.Intake);
    }

    protected void execute(){
        Robot.Intake.stopIntake();
    }
    protected boolean isFinished() {
        return false;
    }
    protected void end(){

    }

    protected void interrupted(){

    }
}