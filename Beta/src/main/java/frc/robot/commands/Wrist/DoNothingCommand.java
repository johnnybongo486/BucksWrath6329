package frc.robot.commands.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DoNothingCommand extends CommandBase {

    public DoNothingCommand() {
        addRequirements(RobotContainer.wrist);
    }

    public void initialize() {
        
    }

    public void execute() {

    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        end();
    }
}