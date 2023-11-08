package frc.robot.Commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ClimberVertical extends CommandBase {

    public ClimberVertical() {
        addRequirements(RobotContainer.climberPiston);
    }

    public void initialize() {
        RobotContainer.climberPiston.climberIn();
    }

    public void execute() {

    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }
}
