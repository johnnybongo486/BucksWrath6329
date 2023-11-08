package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class StopIntake extends CommandBase {

    public StopIntake() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {

    }

    public void execute() {
        RobotContainer.intake.stopIntake();
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotContainer.intake.stopIntake();;
    }

    protected void interrupted() {
        end();
    }
}