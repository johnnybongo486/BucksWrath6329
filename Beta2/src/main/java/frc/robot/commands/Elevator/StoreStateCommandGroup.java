package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StoreStateCommandGroup extends SequentialCommandGroup {
    
    public StoreStateCommandGroup() {
        addCommands(new GoToStorePosition().withTimeout(1).alongWith(new ElevatorIn().withTimeout(0.01)));
    }
}
