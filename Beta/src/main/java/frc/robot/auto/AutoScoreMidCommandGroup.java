package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.HomeStateCommandGroup;
import frc.robot.commands.Elevator.GoToHighPosition;
import frc.robot.commands.Intake.ScoreObject;

public class AutoScoreMidCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreMidCommandGroup() {
        addCommands(
            new AutoElevatorOut().withTimeout(0.5), 
            new GoToHighPosition().withTimeout(0.75),
            new ScoreObject().withTimeout(0.5),
            new HomeStateCommandGroup().withTimeout(0.75)
        );
    }
}
