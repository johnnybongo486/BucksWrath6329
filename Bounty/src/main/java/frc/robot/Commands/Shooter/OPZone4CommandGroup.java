package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OPZone4CommandGroup extends CommandGroup {

	public OPZone4CommandGroup() {
        addSequential(new DeployZone4ShooterPiston(), 0.1);
        addSequential(new SetShooterVelocity(11000));// 2445
    }
}