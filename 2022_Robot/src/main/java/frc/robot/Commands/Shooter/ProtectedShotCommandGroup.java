package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ProtectedShotCommandGroup extends SequentialCommandGroup {

	public ProtectedShotCommandGroup() {
        addCommands(new ShooterPistonDeploy().raceWith(new SetShooterVelocity(13100).alongWith(new SetUpperShooterVelocity(21000)))); 
    }  
}
