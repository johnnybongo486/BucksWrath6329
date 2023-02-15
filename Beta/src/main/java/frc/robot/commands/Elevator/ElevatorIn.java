package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ElevatorIn extends CommandBase {


    public ElevatorIn() {
        addRequirements(RobotContainer.elevatorPiston);
    }

    public void initialize() {
        RobotContainer.elevatorPiston.ElevatorIn();
        RobotContainer.elevatorPiston.setIsTipped(false);
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
