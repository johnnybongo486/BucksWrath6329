package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ScoreHighCommandGroup;
import frc.robot.commands.Intake.ScoreObject;

public class AutoScoreHighConeCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreHighConeCommandGroup() {
        addCommands(
            //new GoToHighPosition().withTimeout(.75),
            //new GoToHighScoreWristPosition().withTimeout(0.4),
            new ScoreHighCommandGroup(),
            new ScoreObject().withTimeout(0.3)
        );
    }
}
