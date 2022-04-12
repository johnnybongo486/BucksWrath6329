package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LeftAutoShotOne extends SequentialCommandGroup {

	public LeftAutoShotOne() {
        addCommands(new ShooterPistonDeploy().raceWith(new SetShooterVelocity(6400).alongWith(new SetUpperShooterVelocity(9600)))); 
    }  
}
