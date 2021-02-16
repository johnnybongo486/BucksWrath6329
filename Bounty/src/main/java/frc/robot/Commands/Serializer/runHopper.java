package frc.robot.Commands.Serializer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class runHopper extends Command {

    public runHopper() {
        requires(Robot.Hopper);
    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.Hopper.runHopper();
    }
     
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.Hopper.stopHopper();
    }

    protected void interrupted() {

    }
} 