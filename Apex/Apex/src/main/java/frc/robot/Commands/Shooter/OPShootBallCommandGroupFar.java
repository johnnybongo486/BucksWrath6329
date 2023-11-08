package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Serializer.reverseSerializer;

public class OPShootBallCommandGroupFar extends CommandGroup {

	public OPShootBallCommandGroupFar() {
        addSequential(new reverseSerializer());
        // addSequential(new VisionShooter());
        addSequential(new SetShooterVelocity(2550));//2400
        //addSequential(new shooterRunSerializer());
    }
}