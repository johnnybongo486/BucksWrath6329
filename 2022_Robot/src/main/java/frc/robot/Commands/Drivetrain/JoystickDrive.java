package frc.robot.Commands.Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Models.*;
public class JoystickDrive extends CommandBase {

    DriveHelper helper;

    private double quickTurnThreshold = 0.2;

    public JoystickDrive() {
        addRequirements(RobotContainer.drivetrain);
        helper = new DriveHelper();
    }

    public void initialize() {
    }

    public void execute() {
        double moveValue = -Robot.robotContainer.getDriverLeftStickY();
        double rotateValue = Robot.robotContainer.getDriverRightStickX();
		boolean quickTurn = (moveValue < quickTurnThreshold && moveValue > -quickTurnThreshold);
        DriveSignal driveSignal = helper.cheesyDrive(moveValue, rotateValue, quickTurn, RobotContainer.drivetrain.getIsHighGear());
        RobotContainer.drivetrain.setDrive(ControlMode.PercentOutput, driveSignal); 
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotContainer.drivetrain.stopDrivetrain();
    }

    protected void interrupted(){
        end();
    }
    
}
