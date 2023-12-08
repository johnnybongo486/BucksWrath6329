package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RunSerializer extends CommandBase {

    public RunSerializer() {
        addRequirements(RobotContainer.serializer);
    }

    public void initialize() {
    }

    public void execute() {
        RobotContainer.serializer.runSerializer();
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotContainer.serializer.stopSerializer();
    }

    protected void interrupted() {
        end();
    }
}
