package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class StoreObject extends CommandBase {

    private boolean isCone;

    public StoreObject() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {
        isCone = RobotContainer.candleSubsystem.getIsCone();
    }

    public void execute() {
        if (isCone == true){
            RobotContainer.intake.holdCone();
        }

        else {
            RobotContainer.intake.holdCube();
        }
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        end();
    }
}