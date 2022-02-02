package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RunIntake extends CommandBase {

    public RunIntake() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {
        
    }

    public void execute() {
        RobotContainer.intake.runIntake();
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotContainer.intake.stopIntake();
    }

    protected void interrupted() {
        end();
    }
}
