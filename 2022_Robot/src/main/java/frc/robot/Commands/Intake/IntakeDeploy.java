package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class IntakeDeploy extends CommandBase {


    public IntakeDeploy() {
        addRequirements(RobotContainer.intakePiston);
    }

    public void initialize() {
        RobotContainer.intakePiston.intakeOut();
    }

    public void execute() {

    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }
}
