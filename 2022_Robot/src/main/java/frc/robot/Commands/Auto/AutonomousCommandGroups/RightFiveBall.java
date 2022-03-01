package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Auto.TurnToAngle;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.StoreIntakeCommandGroup;
import frc.robot.Commands.Serializer.ShootBallCommandGroup;
import frc.robot.Commands.Serializer.StopShooterCommandGroup;
import frc.robot.Commands.Shooter.TarmacShotCommandGroup;

public class RightFiveBall extends SequentialCommandGroup{
   
    public RightFiveBall() {
        addCommands(new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(4).alongWith(new TarmacShotCommandGroup())).withTimeout(2),
        new TurnToAngle(-165, 0.7).raceWith(new StoreIntakeCommandGroup()),
        new MagicAutoDrive(2).withTimeout(1), 
        new ShootBallCommandGroup().withTimeout(1.5),
        new TurnToAngle(-24, 0.7).raceWith(new StopShooterCommandGroup()),
        new MagicAutoDrive(16).alongWith(new IntakeBallCommandGroup()).withTimeout(3),
        new TurnToAngle(-198, 0.7).raceWith(new StoreIntakeCommandGroup()),
        new MagicAutoDrive(17.5).withTimeout(3),
        new TurnToAngle(-150, 0.7),
        new ShootBallCommandGroup().withTimeout(2),
        new StopShooterCommandGroup().withTimeout(0.25)
        );
    }

}
