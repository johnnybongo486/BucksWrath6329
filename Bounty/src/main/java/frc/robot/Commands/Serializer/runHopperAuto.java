package frc.robot.Commands.Serializer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class runHopperAuto extends Command {

    public boolean beamBreak;
    private double runTime = 0;

    public runHopperAuto(double timeout) {
        requires(Robot.Hopper);
        this.runTime = timeout;
    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.Hopper.runHopper();

     }


    protected boolean isFinished() {
        return (this.timeSinceInitialized() >= runTime);
    }
    protected void end() {
        Robot.Hopper.stopHopper();
    }
    protected void interrupted() {
        end();
    }
} 