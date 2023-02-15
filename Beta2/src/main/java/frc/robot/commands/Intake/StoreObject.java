package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class StoreObject extends CommandBase {

    private boolean isCone;

    public StoreObject() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {
    }

    public void execute() {
        isCone = RobotContainer.candleSubsystem.getIsCone();
        if (isCone == true){
            RobotContainer.intake.holdCone();
            RobotContainer.candleSubsystem.setAnimate("Yellow");
        }

        else {
            RobotContainer.intake.holdCube();
            RobotContainer.candleSubsystem.setAnimate("Purple");

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