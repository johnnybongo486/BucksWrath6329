package frc.robot.Commands.Elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ZeroElevator extends Command {
  
	private int homePosition = Robot.LeftElevator.getHomePosition();

    public ZeroElevator() {
       requires(Robot.LeftElevator);
       requires(Robot.RightElevator);
    }

    protected void initialize() {
        Robot.LeftElevator.setTargetPosition(homePosition);  
        Robot.RightElevator.setTargetPosition(homePosition);   
    }

    protected void execute() {
        Robot.LeftElevator.motionMagicControl();
        Robot.RightElevator.motionMagicControl();
    }

    protected boolean isFinished() {
        return Robot.LeftElevator.isInPosition(homePosition) && Robot.RightElevator.isInPosition(homePosition);
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}
