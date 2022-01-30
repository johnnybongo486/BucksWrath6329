package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MagicAutoDriveAngle extends CommandBase {

    public double leftRotations = 0;
    public double rightRotations = 0;
    public double angle = 0;
    public double ticks = 0;
    public double error = 0;
    public double acceptableError = 100;

    public MagicAutoDriveAngle(double distance, double targetAngle) {
        addRequirements(RobotContainer.drivetrain);
        
        this.ticks = distance * 6430;
        this.angle = targetAngle;
    }

    public void initialize() {
        RobotContainer.drivetrain.resetDriveEncoders();   
    }

    public void execute() {
        RobotContainer.drivetrain.magicDriveAngle(ticks, angle);
        RobotContainer.drivetrain.setTargetDistance(ticks);
        error = Math.abs(ticks - RobotContainer.drivetrain.getRightDistance());
        RobotContainer.drivetrain.setDistanceError(error);

    }

    public boolean isFinished() {
        return error < acceptableError;
    }

    protected void end() {
        RobotContainer.drivetrain.stopDrivetrain();
    }

   protected void interrupted(){
        end();
   }
  
}
