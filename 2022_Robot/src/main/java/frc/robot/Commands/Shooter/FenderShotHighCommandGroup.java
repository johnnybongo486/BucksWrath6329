package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FenderShotHighCommandGroup extends SequentialCommandGroup {

	public FenderShotHighCommandGroup() {
        // addCommands(new SetShooterPiston(35).withTimeout(1), new SetShooterVelocity(9800).withTimeout(3));
        addCommands(new SetShooterPiston(40).raceWith(new SetShooterVelocity(15000)));
    }  
}
