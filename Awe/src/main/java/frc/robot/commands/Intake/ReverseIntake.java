package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ReverseIntake extends CommandBase {

    public ReverseIntake() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {
    }

    public void execute() {
        RobotContainer.intake.spitCube();
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