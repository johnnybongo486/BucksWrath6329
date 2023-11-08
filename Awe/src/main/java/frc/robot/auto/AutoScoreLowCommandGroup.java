package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.LowShotCommandGroup;
import frc.robot.commands.ShootCommandGroup;
public class AutoScoreLowCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreLowCommandGroup() {
        addCommands(
            new LowShotCommandGroup(),
            new ShootCommandGroup().withTimeout(2)
        );
    }
}
