package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoAngle;
import frc.robot.Commands.Auto.TurnToAngle;

public class Turn90Degrees extends SequentialCommandGroup{
   
    public Turn90Degrees() {
        addCommands(
        //new TurnToAngle(20, 0.7, 0.008));
        new MagicAutoAngle(90, 0.09));
      //  new TurnToAngle(0, 0, 0).withTimeout(0.01),
      //  new MagicAutoAngle(-40, 0.08));
        // 170 0.0067
        // small angle 0.008

    }

}
