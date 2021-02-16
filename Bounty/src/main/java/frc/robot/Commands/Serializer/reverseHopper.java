package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class reverseHopper extends Command {
   
    private double runTime = 0.5;

    public reverseHopper() {
        requires(Robot.Hopper);
    }

    protected void initialize() {
    
    }

    protected void execute() {
        Robot.Hopper.reverseHopper();
    }

    @Override
    protected boolean isFinished() {
        return (this.timeSinceInitialized() >= runTime);
    }
    
    @Override
    protected void end() {
        Robot.Hopper.stopHopper();

    }

    protected void interrupted() {

    }
}