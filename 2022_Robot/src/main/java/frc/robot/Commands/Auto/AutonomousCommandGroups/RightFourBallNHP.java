package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoAngle;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Auto.TurnToAngle;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.StoreIntakeCommandGroup;
import frc.robot.Commands.Serializer.ShootBallCommandGroup;
import frc.robot.Commands.Serializer.StopShooterCommandGroup;
import frc.robot.Commands.Shooter.FenderShotHighCommandGroup;
import frc.robot.Commands.Shooter.RightAutoShotOne;
import frc.robot.Commands.Shooter.RightAutoShotTwo;
import frc.robot.Commands.Shooter.TarmacShotCommandGroup;

public class RightFourBallNHP extends SequentialCommandGroup{
   
    public RightFourBallNHP() {
        addCommands(
            new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(3, 0.091).alongWith(new FenderShotHighCommandGroup())).withTimeout(1.5),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoAngle(165, 0.095).alongWith(new StoreIntakeCommandGroup().withTimeout(1)),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoDrive(4, 0.091).withTimeout(1),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new ShootBallCommandGroup().withTimeout(1),
            new MagicAutoAngle(72, 0.095).raceWith(new StopShooterCommandGroup()),  // was 74
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoDrive(7.25, 0.11).alongWith(new IntakeBallCommandGroup()).withTimeout(1.5),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoAngle(33, 0.1), //was 34
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoDrive(12.75, 0.091).withTimeout(2),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoAngle(-159, 0.095),
            new TurnToAngle(0, 0, 0).withTimeout(0.01),
            new MagicAutoDrive(12.75, .091).alongWith(new StoreIntakeCommandGroup()).withTimeout(2.5),
            new ShootBallCommandGroup().withTimeout(1),
            new StopShooterCommandGroup().withTimeout(0.05)
        );
    }

}
