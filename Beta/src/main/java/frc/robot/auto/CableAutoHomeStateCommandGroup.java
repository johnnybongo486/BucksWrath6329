package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.StoreStateCommandGroup;
import frc.robot.commands.Intake.StoreObjectCommandGroup;

public class CableAutoHomeStateCommandGroup extends SequentialCommandGroup {
    
    public CableAutoHomeStateCommandGroup() {
        addCommands(new StoreStateCommandGroup().alongWith(new StoreObjectCommandGroup()).withTimeout(2.4)); // was 2
    }

}
