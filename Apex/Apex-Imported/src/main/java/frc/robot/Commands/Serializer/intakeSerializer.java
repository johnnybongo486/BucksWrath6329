package frc.robot.Commands.Serializer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class intakeSerializer extends Command {

    public boolean beamBreak;

    public intakeSerializer() {
        requires(Robot.Serializer);
    }

    protected void initialize() {

    }

    protected void execute() {
        beamBreak = Robot.Serializer.readInput();
        if (beamBreak == false) {
        //beamBreak = Robot.Serializer.readInput();
        Robot.Serializer.slowSerializer();
        }
        else if (beamBreak == true) {
        //beamBreak = Robot.Serializer.readInput();
        Robot.Serializer.SerializerStop();

        }
     }


    protected boolean isFinished() {
        return false;
    }
    protected void end() {

    }
    protected void interrupted() {

    }
} 