package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ElevatorOut extends CommandBase {
    private double readyPosition = RobotContainer.elevator.getReadyPosition() - 6000;

    public ElevatorOut() {
        addRequirements(RobotContainer.elevatorPiston);
    }

    public void initialize() {
        double elevatorPosition = RobotContainer.elevator.getCurrentPosition();
        
        if (elevatorPosition >= readyPosition) {
            RobotContainer.elevatorPiston.ElevatorOut();
            RobotContainer.elevatorPiston.setIsTipped(true);
        }
        
        else {
            RobotContainer.elevatorPiston.ElevatorIn();
            RobotContainer.elevatorPiston.setIsTipped(false);
        }
    }

    public void execute() {
        double elevatorPosition = RobotContainer.elevator.getCurrentPosition();
        
        if (elevatorPosition >= readyPosition) {
            RobotContainer.elevatorPiston.ElevatorOut();
            RobotContainer.elevatorPiston.setIsTipped(true);
        }

        else {
            RobotContainer.elevatorPiston.ElevatorIn();
            RobotContainer.elevatorPiston.setIsTipped(false);
        }
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }
}
