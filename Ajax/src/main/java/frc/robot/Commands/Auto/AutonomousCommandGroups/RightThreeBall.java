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
import frc.robot.Commands.Shooter.RightAutoShotTwo;

public class RightThreeBall extends SequentialCommandGroup{
   
    public RightThreeBall() {
        addCommands(
            new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(3, 0.091).alongWith(new RightAutoShotOne())).withTimeout(1.5),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoAngle(170, 0.09).alongWith(new StoreIntakeCommandGroup()),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new ShootBallCommandGroup().withTimeout(1),
            new MagicAutoAngle(80, 0.09).raceWith(new StopShooterCommandGroup().alongWith(new RightAutoShotTwo().withTimeout(1.5))),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoDrive(7, 0.11).alongWith(new IntakeBallCommandGroup()).withTimeout(2),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoAngle(-125, 0.09).alongWith(new StoreIntakeCommandGroup()), // total angle guess
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new ShootBallCommandGroup().withTimeout(1.5),
            new MagicAutoAngle(140, 0.09).raceWith(new StopShooterCommandGroup()),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(10, 0.091)).withTimeout(3)
        );
    }

}
