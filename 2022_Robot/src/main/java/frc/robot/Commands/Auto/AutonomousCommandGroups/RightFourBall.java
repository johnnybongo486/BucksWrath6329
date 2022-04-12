package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoAngle;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Auto.TurnToAngle;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.StoreIntakeCommandGroup;
import frc.robot.Commands.Serializer.ShootBallCommandGroup;
import frc.robot.Commands.Serializer.StopShooterCommandGroup;
import frc.robot.Commands.Shooter.LeftAutoShotOne;
import frc.robot.Commands.Shooter.RightAutoShotOne;
import frc.robot.Commands.Shooter.RightAutoShotTwo;

public class RightFourBall extends SequentialCommandGroup{
   
    public RightFourBall() {
        addCommands(
        new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(4, 0.11).alongWith(new RightAutoShotOne())).withTimeout(2),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoAngle(-165, 0.08).raceWith(new StoreIntakeCommandGroup()),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new ShootBallCommandGroup().withTimeout(1),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoAngle(151, 0.08).raceWith(new StopShooterCommandGroup()),  // blue 151 // 149 red
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoDrive(12.25, 0.11).alongWith(new IntakeBallCommandGroup()).withTimeout(2),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoDrive(-1, 0.11).withTimeout(2.5).alongWith(new RightAutoShotTwo().withTimeout(2.5)),
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoAngle(-172, 0.08),  // blue -172  // red -173
        new TurnToAngle(0, 0, 0).withTimeout(0.01),
        new MagicAutoDrive(6, 0.11).alongWith(new StoreIntakeCommandGroup()).withTimeout(2),
        new ShootBallCommandGroup().withTimeout(1),
        new StopShooterCommandGroup().withTimeout(0.05)
        );
    }

}
