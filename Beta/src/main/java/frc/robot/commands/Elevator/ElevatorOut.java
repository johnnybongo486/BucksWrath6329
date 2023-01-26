package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ElevatorOut extends CommandBase {


    public ElevatorOut() {
        addRequirements(RobotContainer.elevatorPiston);
    }

    public void initialize() {
        RobotContainer.elevatorPiston.ElevatorOut();
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
