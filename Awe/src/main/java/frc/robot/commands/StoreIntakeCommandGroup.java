package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Wrist.GoToStoreWristPosition;
import frc.robot.commands.Intake.StopIntake;

public class StoreIntakeCommandGroup extends SequentialCommandGroup {
    
    public StoreIntakeCommandGroup() {
        addCommands(new StopIntake().withTimeout(0.1).andThen(new GoToStoreWristPosition()));
    }

}