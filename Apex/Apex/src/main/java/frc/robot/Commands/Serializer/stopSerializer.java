package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class stopSerializer extends Command {
   
    public stopSerializer() {
    requires(Robot.Serializer);
    }

    protected void initialize() {
        Robot.Serializer.SerializerStop();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}