package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ReadyStateCommandGroup extends SequentialCommandGroup {
    
    public ReadyStateCommandGroup() {
        addCommands(new GoToReadyPosition().alongWith(new ElevatorOut()).withTimeout(1));
    }
}
