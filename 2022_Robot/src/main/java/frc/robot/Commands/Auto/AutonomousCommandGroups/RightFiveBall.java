package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Auto.TurnToAngle;

public class RightFiveBall extends SequentialCommandGroup{
   
    public RightFiveBall() {
        addCommands(new MagicAutoDrive(3).withTimeout(3),
                    new TurnToAngle(170, 0.3).withTimeout(1),
                    new TurnToAngle(230, 0.3).withTimeout(1),
                    new MagicAutoDrive(9).withTimeout(3)
                    );
    }

}
