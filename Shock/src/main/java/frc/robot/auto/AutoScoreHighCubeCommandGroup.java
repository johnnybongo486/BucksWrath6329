package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.GoToHighPosition;
import frc.robot.commands.Intake.ScoreObject;
public class AutoScoreHighCubeCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreHighCubeCommandGroup() {
        addCommands(
            new GoToHighPosition().withTimeout(0.75),
            new ScoreObject().withTimeout(0.5)
        );
    }
}
