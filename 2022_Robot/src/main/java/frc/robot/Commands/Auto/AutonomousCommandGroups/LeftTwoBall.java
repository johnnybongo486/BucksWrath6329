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

public class LeftTwoBall extends SequentialCommandGroup{
   
    public LeftTwoBall() {
        addCommands(
            new IntakeBallCommandGroup().withTimeout(0.5),
            new MagicAutoDrive(3.5, 0.08).alongWith(new RightAutoShotOne()).withTimeout(1.5),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoAngle(-167, 0.09).raceWith(new StoreIntakeCommandGroup()),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new ShootBallCommandGroup().withTimeout(1),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoAngle(-95, 0.09).raceWith(new StopShooterCommandGroup())
            //new TurnToAngle(0, 0, 0).withTimeout(0.1),
            //new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(18.5, 0.11)).withTimeout(4),
            //new TurnToAngle(0, 0, 0).withTimeout(0.1),
            //new MagicAutoAngle(155, 0.08),
            //new TurnToAngle(0, 0, 0).withTimeout(0.1),
            //new MagicAutoDrive(14.5, 0.11).raceWith(new StoreIntakeCommandGroup()).withTimeout(4),
            //new ShootBallCommandGroup().withTimeout(1.5),
            //new StopShooterCommandGroup().withTimeout(0.05)
            );
    }

}
