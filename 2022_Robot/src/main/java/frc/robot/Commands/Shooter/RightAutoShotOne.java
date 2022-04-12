package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RightAutoShotOne extends SequentialCommandGroup {

	public RightAutoShotOne() {
        addCommands(new ShooterPistonDeploy().raceWith(new SetShooterVelocity(6375).alongWith(new SetUpperShooterVelocity(9550)))); // 9300 and 15500
    }  
}
