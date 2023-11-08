package frc.robot.Commands.Serializer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class shooterRunSerializer extends Command {

    public shooterRunSerializer() {
        requires(Robot.Serializer);
    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.Serializer.runSerializer();
    }
     
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.Serializer.SerializerStop();
    }

    protected void interrupted() {

    }
} 