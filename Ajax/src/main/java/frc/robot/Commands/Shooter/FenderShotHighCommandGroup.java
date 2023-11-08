package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FenderShotHighCommandGroup extends SequentialCommandGroup {

	public FenderShotHighCommandGroup() {
        addCommands(new ShooterPistonStore().raceWith(new SetShooterVelocity(6100).alongWith(new SetUpperShooterVelocity(9150)))); 
    }  
}
