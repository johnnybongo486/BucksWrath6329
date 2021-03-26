package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class JoystickSlideDrive extends Command {

    public double slideValue;
    public double absSlideValue;

    public JoystickSlideDrive() {
       requires(Robot.SlideDrive);
    }

    protected void initialize() {

    }

    protected void execute() {
        slideValue = Robot.oi.getDriverLeftStickX();
        absSlideValue = Math.abs(slideValue);

        if (absSlideValue >= 0.1) {
            Robot.SlideDrive.setSlideActive(false); // should be true
        }

        else {
            Robot.SlideDrive.setSlideActive(false);
        }

        // Robot.SlideDrive.drive(slideValue);       
    }

    protected boolean isFinished() {
       return false;
    }

    protected void end() {

    }

    protected void interrupted(){

    }
  
}