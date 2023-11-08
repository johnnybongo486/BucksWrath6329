package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RightAutoShotTwo extends SequentialCommandGroup {

	public RightAutoShotTwo() {
        addCommands(new ShooterPistonDeploy().raceWith(new SetShooterVelocity(7000).alongWith(new SetUpperShooterVelocity(10500)))); 
    }  
}
