package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ReverseSerializer extends CommandBase {

    public ReverseSerializer() {
        addRequirements(RobotContainer.serializer);
    }

    public void initialize() {
        
    }

    public void execute() {
        RobotContainer.serializer.reverseSerializer();
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
