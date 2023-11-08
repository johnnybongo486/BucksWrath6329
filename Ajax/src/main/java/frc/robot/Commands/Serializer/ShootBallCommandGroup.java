package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Intake.RunCenterIntake;

public class ShootBallCommandGroup extends SequentialCommandGroup{
    public ShootBallCommandGroup() {
        addCommands(
            new RunSerializer().alongWith(new RunCenterIntake())
        );
    }
}
