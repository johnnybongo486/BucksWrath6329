package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FenderShotLowCommandGroup extends SequentialCommandGroup {

	public FenderShotLowCommandGroup() {
        addCommands(new ShooterPistonDeploy().raceWith(new SetShooterVelocity(2500).alongWith(new SetUpperShooterVelocity(5000))));
    }  
}
