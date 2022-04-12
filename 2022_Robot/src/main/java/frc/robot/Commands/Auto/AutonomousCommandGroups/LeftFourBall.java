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
import frc.robot.Commands.Shooter.TarmacShotCommandGroup;

public class LeftFourBall extends SequentialCommandGroup{
   
    public LeftFourBall() {
        addCommands(
                //new IntakeBallCommandGroup().withTimeout(0.5),
                new MagicAutoDrive(-4.5, 0.08).alongWith(new LeftAutoShotOne()).withTimeout(1.5),
                new TurnToAngle(0, 0, 0).withTimeout(0.01),
                //new MagicAutoAngle(-170, 0.09).raceWith(new StoreIntakeCommandGroup()),
                //new TurnToAngle(0, 0, 0).withTimeout(0.01),
                new ShootBallCommandGroup().withTimeout(1.5),
                new StopShooterCommandGroup().withTimeout(0.05)
                //new MagicAutoAngle(-94.5, 0.09).raceWith(new StopShooterCommandGroup()),
                //new TurnToAngle(0, 0, 0).withTimeout(0.01),
                //new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(18, 0.11)).withTimeout(4),
                //new TurnToAngle(0, 0, 0).withTimeout(0.01),
                //new MagicAutoAngle(143, 0.09),
                // new TurnToAngle(0, 0, 0).withTimeout(0.01),
                // new MagicAutoDrive(12, 0.091).alongWith(new StoreIntakeCommandGroup()).withTimeout(2.5),
                // new ShootBallCommandGroup().withTimeout(1),
                // new StopShooterCommandGroup().withTimeout(0.05)
                );
    }

}
