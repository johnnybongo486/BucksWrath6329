package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OPZone2CommandGroup extends CommandGroup {

	public OPZone2CommandGroup() {
        addSequential(new DeployZone2ShooterPiston(), 0.25);
        addSequential(new SetShooterVelocity(11000));// 2445
    }
}