package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TarmacShotCommandGroup extends SequentialCommandGroup {

	public TarmacShotCommandGroup() {
        addCommands(new SetShooterPiston(80).withTimeout(1), new SetShooterVelocity(11000).withTimeout(3));
    }  
}
