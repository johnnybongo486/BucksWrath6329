package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OPZone1CommandGroup extends SequentialCommandGroup {

	public OPZone1CommandGroup() {
        addCommands(new DeployZone1ShooterPiston().withTimeout(0.1), new SetShooterVelocity(8000).withTimeout(3));
    }  
}
