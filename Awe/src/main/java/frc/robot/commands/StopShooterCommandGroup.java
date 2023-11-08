package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Shooter.SetLowerShooterVelocity;
import frc.robot.commands.Shooter.SetUpperShooterVelocity;

public class StopShooterCommandGroup extends SequentialCommandGroup {
    
    public StopShooterCommandGroup() {
        addCommands(new SetUpperShooterVelocity(0)
            .alongWith(new SetLowerShooterVelocity(1000)));
    }

}