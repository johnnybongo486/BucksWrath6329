package frc.robot.Commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ZeroWrist extends Command {
  
    private int homePosition = Robot.Wrist.getHomePosition();

    public ZeroWrist() {
        requires(Robot.Wrist);
    }

    protected void initialize() {
        Robot.Wrist.setTargetPosition(homePosition);
    }

    protected void execute() {
        Robot.Wrist.motionMagicControl();
    }

    protected boolean isFinished() {
        return Robot.Wrist.isInPosition(homePosition);
    }

    protected void end() {
        
    }

    protected void interrupted() {

    }
}
