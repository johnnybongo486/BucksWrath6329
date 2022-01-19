package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class LowGear extends CommandBase {

    public LowGear() {
        addRequirements(RobotContainer.shifter);
    }

    public void initialize() {
        RobotContainer.shifter.lowGear();
    }

    public void execute() {

    }

    public boolean isFinished() {
        return false; // || Robot.Drivetrain.getSpeed() > 10;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }
}
