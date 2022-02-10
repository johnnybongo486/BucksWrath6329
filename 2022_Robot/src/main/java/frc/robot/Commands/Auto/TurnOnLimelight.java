package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class TurnOnLimelight extends CommandBase {

    public TurnOnLimelight() {
        addRequirements(RobotContainer.limelight);
    }

    public void initialize() {
        RobotContainer.limelight.visionMode();
        
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