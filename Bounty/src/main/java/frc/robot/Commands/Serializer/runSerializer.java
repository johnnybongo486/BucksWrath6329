package frc.robot.Commands.Serializer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class runSerializer extends Command {

    public runSerializer() {
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
        
    }

    protected void interrupted() {

    }
} 