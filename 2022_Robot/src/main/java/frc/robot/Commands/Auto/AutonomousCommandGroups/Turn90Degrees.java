package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.TurnToAngle;

public class Turn90Degrees extends SequentialCommandGroup{
   
    public Turn90Degrees() {
        addCommands(new TurnToAngle(90, 0.3));
    }

}
