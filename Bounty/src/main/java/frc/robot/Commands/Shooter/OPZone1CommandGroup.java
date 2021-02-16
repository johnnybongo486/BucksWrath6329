package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OPZone1CommandGroup extends CommandGroup {

	public OPZone1CommandGroup() {
        addSequential(new DeployZone1ShooterPiston(), 0.25);
        addSequential(new SetShooterVelocity(10000));// 2445
    }
}