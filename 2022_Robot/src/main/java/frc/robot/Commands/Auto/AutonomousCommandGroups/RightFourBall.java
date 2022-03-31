package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoAngle;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Auto.TurnToAngle;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.StoreIntakeCommandGroup;
import frc.robot.Commands.Serializer.ShootBallCommandGroup;
import frc.robot.Commands.Serializer.StopShooterCommandGroup;
import frc.robot.Commands.Shooter.RightAutoShotOne;

public class RightFourBall extends SequentialCommandGroup{
   
    public RightFourBall() {
        addCommands(
        new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(4, 0.11).alongWith(new RightAutoShotOne())).withTimeout(2),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoAngle(-163, 0.08).raceWith(new StoreIntakeCommandGroup()),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new ShootBallCommandGroup().withTimeout(1),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoAngle(153, 0.08).raceWith(new StopShooterCommandGroup()),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoDrive(12.25, 0.11).alongWith(new IntakeBallCommandGroup()).withTimeout(3),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoAngle(-166, 0.08),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoDrive(12.5, 0.11).withTimeout(3),
        new ShootBallCommandGroup().withTimeout(1),
        new StopShooterCommandGroup().withTimeout(0.05)
        );
    }

}
