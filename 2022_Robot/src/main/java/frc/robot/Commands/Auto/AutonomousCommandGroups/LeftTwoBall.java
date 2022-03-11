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

public class LeftTwoBall extends SequentialCommandGroup{
   
    public LeftTwoBall() {
        addCommands(
            new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(4, 0.08).alongWith(new TarmacShotCommandGroup())).withTimeout(1.75),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoAngle(-170, 0.08),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoDrive(3, 0.08).raceWith(new StoreIntakeCommandGroup()).withTimeout(1.5), 
            new ShootBallCommandGroup().withTimeout(1.5),
            new TurnToAngle(0, 0, 0).withTimeout(0.1),
            new MagicAutoAngle(-95, 0.08).raceWith(new StopShooterCommandGroup())
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
