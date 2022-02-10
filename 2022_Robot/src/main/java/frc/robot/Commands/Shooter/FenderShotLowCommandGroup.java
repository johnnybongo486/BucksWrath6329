package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FenderShotLowCommandGroup extends SequentialCommandGroup {

	public FenderShotLowCommandGroup() {
        addCommands(new SetShooterPiston(90).withTimeout(1), new SetShooterVelocity(6000).withTimeout(3));
    }  
}
