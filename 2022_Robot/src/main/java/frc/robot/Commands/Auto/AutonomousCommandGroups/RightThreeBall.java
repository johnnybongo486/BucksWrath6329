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

public class RightThreeBall extends SequentialCommandGroup{
   
    public RightThreeBall() {
        addCommands(
            new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(3, 0.091).alongWith(new TarmacShotCommandGroup())).withTimeout(1.5),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoAngle(170, 0.08),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoDrive(2, 0.08).raceWith(new StoreIntakeCommandGroup()),
            new ShootBallCommandGroup().withTimeout(1.5),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoAngle(92, 0.08).raceWith(new StopShooterCommandGroup()),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoDrive(7, 0.11).alongWith(new IntakeBallCommandGroup()).withTimeout(2),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoAngle(-70, 0.1), // total angle guess
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoDrive(5, 0.009).alongWith(new StoreIntakeCommandGroup()).withTimeout(2),  // total distance guess
            new ShootBallCommandGroup().withTimeout(1.5),
            new StopShooterCommandGroup().withTimeout(0.05)
        );
    }

}
