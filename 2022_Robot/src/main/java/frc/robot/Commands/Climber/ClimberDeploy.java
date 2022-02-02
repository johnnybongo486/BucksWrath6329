package frc.robot.Commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ClimberDeploy extends CommandBase {

    public ClimberDeploy() {
        addRequirements(RobotContainer.climberPiston);
    }

    public void initialize() {
        RobotContainer.climberPiston.climberOut();
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
