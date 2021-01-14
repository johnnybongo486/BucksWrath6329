package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Serializer.reverseSerializer;

public class OPShootBallCommandGroupMid extends CommandGroup {

    public OPShootBallCommandGroupMid() {
        addSequential(new reverseSerializer());
        // addSequential(new VisionShooter());
        addSequential(new SetShooterVelocity(2250));
       // addSequential(new shooterRunSerializer());
    }
}