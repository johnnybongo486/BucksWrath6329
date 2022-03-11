package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class AutoShifter extends CommandBase {

    double avgSpeed = 0;
    double leftSpeed = 0;
    double rightSpeed = 0;

    public AutoShifter() {
        addRequirements(RobotContainer.shifter);
    }

    public void initialize() {
        RobotContainer.shifter.highGear();
    }

    public void execute() {
        leftSpeed = Math.abs(RobotContainer.drivetrain.getLeftSpeed());
        rightSpeed = Math.abs(RobotContainer.drivetrain.getRightSpeed());
        avgSpeed = (leftSpeed + rightSpeed) / 2;

        if (avgSpeed < 15000 && avgSpeed > 0) {
            RobotContainer.shifter.lowGear();
            RobotContainer.drivetrain.setIsHighGear(false);
        }

        if (avgSpeed > 17000) {
            RobotContainer.shifter.highGear();
            RobotContainer.drivetrain.setIsHighGear(true);
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
