package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OPZone1CommandGroup extends SequentialCommandGroup {

	public OPZone1CommandGroup() {
        addCommands(new DeployZone1ShooterPiston(), new SetShooterVelocity(12000));
    }  
}
