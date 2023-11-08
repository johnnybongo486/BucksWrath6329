package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetCubeMode extends CommandBase {
    
    public SetCubeMode() {
        addRequirements(RobotContainer.candleSubsystem);
    }

    public void initialize() {
        RobotContainer.candleSubsystem.setIsCone(false);
        RobotContainer.candleSubsystem.setAnimate("Purple");
    }

    public void execute() {
        
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
