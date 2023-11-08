package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Models.*;
public class JoystickDrive extends Command {

    DriveHelper helper;

    private double quickTurnThreshold = 0.2;

    public JoystickDrive() {
        requires(Robot.Drivetrain);
        helper = new DriveHelper();
    }

    protected void initialize() {
    }

    protected void execute() {
        double moveValue = Robot.oi.getDriverLeftStickY();
        double rotateValue = Robot.oi.getDriverRightStickX()/2;
		boolean quickTurn = (moveValue < quickTurnThreshold && moveValue > -quickTurnThreshold);
        DriveSignal driveSignal = helper.cheesyDrive(-moveValue, rotateValue, quickTurn, false);
        Robot.Drivetrain.drive(driveSignal); 
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.Drivetrain.stopDrivetrain();
    }

    protected void interrupted(){
        end();
    }
    
}