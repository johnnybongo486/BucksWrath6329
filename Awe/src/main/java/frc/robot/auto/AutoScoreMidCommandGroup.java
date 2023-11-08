package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.MidShotCommandGroup;
import frc.robot.commands.ShootCommandGroup;
public class AutoScoreMidCommandGroup extends SequentialCommandGroup {
    
    public AutoScoreMidCommandGroup() {
        addCommands(
            new MidShotCommandGroup(),
            new ShootCommandGroup().withTimeout(2)
        );
    }
}
