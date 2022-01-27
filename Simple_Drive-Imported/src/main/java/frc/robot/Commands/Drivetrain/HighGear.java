package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class HighGear extends CommandBase {

    public HighGear() {
        addRequirements(RobotContainer.shifter);
    }

    public void initialize() {
        RobotContainer.shifter.highGear();
    }

    public void execute() {

    }

    public boolean isFinished() {
        return false; // || Robot.Drivetrain.getSpeed() < 10;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }
}
