package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class TurnOffLimelight extends CommandBase {

    public TurnOffLimelight() {
        addRequirements(RobotContainer.limelight);
    }

    public void initialize() {
        RobotContainer.limelight.cameraMode();
        
    }
    public void execute() {
       
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted(){
    }
}