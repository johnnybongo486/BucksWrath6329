package frc.robot.Commands.Serializer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class runSerializerAuto extends Command {

    public boolean beamBreak;
    private double runTime = 0;

    public runSerializerAuto(double timeout) {
        requires(Robot.Serializer);
        this.runTime = timeout;
    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.Serializer.runSerializer();

     }


    protected boolean isFinished() {
        return (this.timeSinceInitialized() >= runTime);
    }
    protected void end() {
        Robot.Serializer.stopSerializer();
    }
    protected void interrupted() {
        end();
    }
} 