package frc.robot.Commands.Serializer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class intakeSerializerAuto extends Command {

    public boolean beamBreak;
    private double runTime = 0;

    public intakeSerializerAuto(double timeout) {
        requires(Robot.Serializer);
        this.runTime = timeout;
    }

    protected void initialize() {

    }

    protected void execute() {
        beamBreak = Robot.Serializer.readInput();

        if (beamBreak == false) {
        beamBreak = Robot.Serializer.readInput();
        Robot.Serializer.slowSerializer();
        }

        else if (beamBreak == true) {
        beamBreak = Robot.Serializer.readInput();
        Robot.Serializer.SerializerStop();
        }
     }


    protected boolean isFinished() {
        return (this.timeSinceInitialized() >= runTime);
    }
    protected void end() {
        Robot.Serializer.SerializerStop();
    }
    protected void interrupted() {
        end();
    }
} 