package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class StopSerializer extends CommandBase {

    public StopSerializer() {
        addRequirements(RobotContainer.serializer);
    }

    public void initialize() {
    }

    public void execute() {
        RobotContainer.serializer.stopSerializer();
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
