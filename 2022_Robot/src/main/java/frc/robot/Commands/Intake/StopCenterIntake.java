package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class StopCenterIntake extends CommandBase {

    public StopCenterIntake() {
        addRequirements(RobotContainer.centerIntake);
    }

    public void initialize() {
        
    }

    public void execute() {
        RobotContainer.centerIntake.stopCenterIntake();
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
