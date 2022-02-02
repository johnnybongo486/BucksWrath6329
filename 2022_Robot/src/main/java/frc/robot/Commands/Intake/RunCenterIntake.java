package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RunCenterIntake extends CommandBase {

    public RunCenterIntake() {
        addRequirements(RobotContainer.centerIntake);
    }

    public void initialize() {
        
    }

    public void execute() {
        RobotContainer.centerIntake.runCenterIntake();
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotContainer.centerIntake.stopCenterIntake();
    }

    protected void interrupted() {
        end();
    }
}
