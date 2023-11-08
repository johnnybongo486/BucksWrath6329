package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Serializer.reverseSerializer;

public class OPShootBallCommandGroupClose extends CommandGroup {

	public OPShootBallCommandGroupClose() {
        addSequential(new reverseSerializer());
        // addSequential(new VisionShooter());
        addSequential(new SetShooterVelocity(1900));// 2445
       // addSequential(new shooterRunSerializer());
    }
}