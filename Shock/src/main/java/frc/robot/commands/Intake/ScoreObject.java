package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ScoreObject extends CommandBase {

    private boolean isCone;

    public ScoreObject() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {
    }

    public void execute() {
        isCone = RobotContainer.candleSubsystem.getIsCone();
        if (isCone == true){
            RobotContainer.intake.scoreCone();
        }

        else {
            RobotContainer.intake.scoreCube();
        }
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