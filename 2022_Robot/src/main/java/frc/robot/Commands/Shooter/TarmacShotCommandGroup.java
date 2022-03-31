package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TarmacShotCommandGroup extends SequentialCommandGroup {

	public TarmacShotCommandGroup() {
        addCommands(new ShooterPistonDeploy().raceWith(new SetShooterVelocity(9400).alongWith(new SetUpperShooterVelocity(11800)))); 
    }  
}
