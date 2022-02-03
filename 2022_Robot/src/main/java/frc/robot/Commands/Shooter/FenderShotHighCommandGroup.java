package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FenderShotHighCommandGroup extends SequentialCommandGroup {

	public FenderShotHighCommandGroup() {
        addCommands(new SetShooterPiston(45).withTimeout(0.1), new SetShooterVelocity(8000).withTimeout(3));
    }  
}
