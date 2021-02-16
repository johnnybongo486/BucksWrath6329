package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Serializer.reverseSerializer;
import frc.robot.Commands.Serializer.shooterRunSerializer;

public class CloseShootBallCommandGroup extends CommandGroup {

	public CloseShootBallCommandGroup() {
        addSequential(new reverseSerializer());
        // addSequential(new VisionShooter());
        addSequential(new SetShooterVelocity(1200));
        addSequential(new shooterRunSerializer());
    }
}