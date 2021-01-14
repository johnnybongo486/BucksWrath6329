package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Serializer.shooterRunSerializer;

public class ShootBallCommandGroup extends CommandGroup {

	public ShootBallCommandGroup() {
       // addSequential(new reverseSerializer());
       // addSequential(new VisionShooter());
        addSequential(new shooterRunSerializer());
    }
}