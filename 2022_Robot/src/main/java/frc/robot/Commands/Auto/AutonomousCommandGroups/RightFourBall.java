package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoAngle;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Auto.TurnToAngle;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.StoreIntakeCommandGroup;
import frc.robot.Commands.Serializer.ShootBallCommandGroup;
import frc.robot.Commands.Serializer.StopShooterCommandGroup;
import frc.robot.Commands.Shooter.TarmacShotCommandGroup;

public class RightFourBall extends SequentialCommandGroup{
   
    public RightFourBall() {
        addCommands(
        new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(4.5, 0.11).alongWith(new TarmacShotCommandGroup())).withTimeout(2),
        new TurnToAngle(0, 0, 0).withTimeout(0.1),
        new MagicAutoAngle(-163, 0.08).raceWith(new StoreIntakeCommandGroup()),
        new TurnToAngle(0, 0, 0).withTimeout(0.1),
        new MagicAutoDrive(2.5, 0.05).withTimeout(1.5), 
        new ShootBallCommandGroup().withTimeout(1.5),
        new TurnToAngle(0, 0, 0).withTimeout(0.1),
        new MagicAutoAngle(156, 0.08).raceWith(new StopShooterCommandGroup()),
        new TurnToAngle(0, 0, 0).withTimeout(0.1),
        new MagicAutoDrive(14.5, 0.11).alongWith(new IntakeBallCommandGroup()).withTimeout(3),
        new TurnToAngle(0, 0, 0).withTimeout(0.1),
        new MagicAutoAngle(-165, 0.08),
        new TurnToAngle(0, 0, 0).withTimeout(0.1),
        new MagicAutoDrive(14.5, 0.11).withTimeout(3).raceWith(new StoreIntakeCommandGroup()),
        new ShootBallCommandGroup().withTimeout(1.5),
        new StopShooterCommandGroup().withTimeout(0.05)
        );
    }

}
