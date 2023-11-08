package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Intake.StopCenterIntake;

public class StopShooterCommandGroup extends SequentialCommandGroup{
    
    public StopShooterCommandGroup() {
        addCommands(
            new StopSerializer().alongWith(new StopCenterIntake())
        );
    }
}
