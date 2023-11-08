package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.GoToHighPosition;
import frc.robot.commands.Wrist.DoNothingCommand;
import frc.robot.commands.Wrist.GoToHighScoreWristPosition;

public class ScoreHighCommandGroup extends SequentialCommandGroup {
    
    public ScoreHighCommandGroup() {
        addCommands(new GoToHighPosition().alongWith(new DoNothingCommand().withTimeout(0.5).andThen(new GoToHighScoreWristPosition())));
    }

}
