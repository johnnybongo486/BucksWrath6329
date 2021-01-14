package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class AutoShifter extends Command {

    public double speed = 0;

    public AutoShifter() {
        requires(Robot.Shifter);
    }

    protected void initialize() {
        Robot.Shifter.LowGear();
    }

    protected void execute () {
        speed = Robot.Drivetrain.getLeftSpeed();
        speed = Math.abs(speed);

        if (speed >= 3000) {
            Robot.Shifter.HighGear();
        }
        else if (speed <= 2000) {
            Robot.Shifter.LowGear();
        }
        else {
            
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }


    protected void interrupted() {

    }
}