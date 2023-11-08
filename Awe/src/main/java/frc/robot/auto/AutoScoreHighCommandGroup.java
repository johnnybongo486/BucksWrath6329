package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.HighShotCommandGroup;
import frc.robot.commands.ShootCommandGroup;
public class AutoScoreHighCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreHighCommandGroup() {
        addCommands(
            new HighShotCommandGroup(),
            new ShootCommandGroup().withTimeout(2)
        );
    }
}
