package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.GoToMidPosition;
import frc.robot.commands.Intake.ScoreObject;

public class AutoScoreMidCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreMidCommandGroup() {
        addCommands(
            new GoToMidPosition().withTimeout(0.5),
            new ScoreObject().withTimeout(0.5)        );
    }
}
