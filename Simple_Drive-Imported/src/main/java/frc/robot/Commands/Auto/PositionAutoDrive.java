package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class PositionAutoDrive extends CommandBase {

    public double leftTicks = 0;
    public double rightTicks = 0;
    public double leftError = 0;
    public double rightError = 0;
    public double acceptableError = 100;


    public PositionAutoDrive(double leftDistance, double rightDistance) {
        addRequirements(RobotContainer.drivetrain);
        this.leftTicks = leftDistance * 6430;
        this.rightTicks = rightDistance * 6430;
    }

    public void initialize() {
        RobotContainer.drivetrain.resetDriveEncoders();      
    }

    public void execute() {
    RobotContainer.drivetrain.smartDrive(leftTicks, rightTicks);
        leftError = Math.abs(leftTicks - RobotContainer.drivetrain.getLeftDistance());
        rightError = Math.abs(rightTicks - RobotContainer.drivetrain.getRightDistance());
        RobotContainer.drivetrain.setTargetDistance(leftTicks);
        RobotContainer.drivetrain.setDistanceError(leftTicks - RobotContainer.drivetrain.getLeftDistance());
    }

    public boolean isFinished() {
        return leftError < acceptableError && rightError < acceptableError;
    }

    protected void end() {
        RobotContainer.drivetrain.stopDrivetrain();
    }

    protected void interrupted(){
        end();
    }
}
