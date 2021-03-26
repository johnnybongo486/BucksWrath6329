package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OPZone3CommandGroup extends CommandGroup {

	public OPZone3CommandGroup() {
        addSequential(new DeployZone3ShooterPiston(), 0.1);
        addSequential(new SetShooterVelocity(11000));// 2445
    }
}