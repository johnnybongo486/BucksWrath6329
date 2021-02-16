package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class reverseSerializer extends Command {
   
    private double runTime = 0.5;

    public reverseSerializer() {
        requires(Robot.Serializer);
    }

    protected void initialize() {
    
    }

    protected void execute() {
        Robot.Serializer.reverseSerializer();
    }

    @Override
    protected boolean isFinished() {
        return (this.timeSinceInitialized() >= runTime);
    }
    
    @Override
    protected void end() {
        Robot.Serializer.stopSerializer();

    }

    protected void interrupted() {

    }
}