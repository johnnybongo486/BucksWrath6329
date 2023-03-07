package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class AutoElevatorOut extends CommandBase  {
    public AutoElevatorOut() {
        addRequirements(RobotContainer.elevatorPiston);
    }

    public void initialize() {        
            RobotContainer.elevatorPiston.ElevatorOut();
            RobotContainer.elevatorPiston.setIsTipped(true);
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
