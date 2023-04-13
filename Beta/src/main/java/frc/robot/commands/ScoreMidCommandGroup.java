package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.GoToMidPosition;
import frc.robot.commands.Wrist.GoToMidScoreWristPosition;

public class ScoreMidCommandGroup extends SequentialCommandGroup {
    
    public ScoreMidCommandGroup() {
        addCommands(new GoToMidPosition().alongWith(new GoToMidScoreWristPosition()));
    }

}
