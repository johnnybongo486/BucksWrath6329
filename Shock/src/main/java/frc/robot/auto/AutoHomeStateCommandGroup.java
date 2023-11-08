package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.StoreStateCommandGroup;
import frc.robot.commands.Intake.StoreObjectCommandGroup;

public class AutoHomeStateCommandGroup extends SequentialCommandGroup {
    
    public AutoHomeStateCommandGroup() {
        addCommands(new StoreStateCommandGroup().alongWith(new StoreObjectCommandGroup()).withTimeout(1));
    }

}
