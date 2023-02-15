package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Wrist.GoToFloorIntakePosition;

public class FloorIntakeCommandGroup extends SequentialCommandGroup {
    
    public FloorIntakeCommandGroup() {
        addCommands(new GoToFloorIntakePosition().withTimeout(1), new IntakeObject());
    }
}
