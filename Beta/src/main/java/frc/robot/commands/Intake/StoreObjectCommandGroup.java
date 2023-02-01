package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Wrist.GoToStoreWristPosition;

public class StoreObjectCommandGroup extends SequentialCommandGroup {
    
    public StoreObjectCommandGroup() {
        addCommands(new GoToStoreWristPosition().withTimeout(1).alongWith(new StoreObject()));
    }
}
