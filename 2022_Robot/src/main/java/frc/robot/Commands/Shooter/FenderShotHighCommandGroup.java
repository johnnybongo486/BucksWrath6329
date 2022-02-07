package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FenderShotHighCommandGroup extends SequentialCommandGroup {

	public FenderShotHighCommandGroup() {
        addCommands(new SetShooterPiston(40).withTimeout(0.1), new SetShooterVelocity(10500).withTimeout(3));
    }  
}
