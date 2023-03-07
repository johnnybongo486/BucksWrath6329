package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.GoToHighPosition;
import frc.robot.commands.Intake.ScoreObject;
import frc.robot.commands.Wrist.GoToHighScoreWristPosition;

public class AutoScoreHighConeCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreHighConeCommandGroup() {
        addCommands(
            new GoToHighPosition().withTimeout(.75),
            new GoToHighScoreWristPosition().withTimeout(0.5),
            new ScoreObject().withTimeout(0.5)
        );
    }
}
