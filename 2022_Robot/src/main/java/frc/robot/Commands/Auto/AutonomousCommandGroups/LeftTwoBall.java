package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Auto.TurnToAngle;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.StoreIntakeCommandGroup;
import frc.robot.Commands.Serializer.ShootBallCommandGroup;
import frc.robot.Commands.Serializer.StopShooterCommandGroup;
import frc.robot.Commands.Shooter.TarmacShotCommandGroup;

public class LeftTwoBall extends SequentialCommandGroup{
   
    public LeftTwoBall() {
        addCommands(new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(3.5).alongWith(new TarmacShotCommandGroup())).withTimeout(3),
                    new StoreIntakeCommandGroup().withTimeout(0.25),
                    new TurnToAngle(-170, 0.5),
                    new MagicAutoDrive(2).withTimeout(1.5), 
                    new ShootBallCommandGroup().withTimeout(2),
                    new TurnToAngle(-270, 0.5).raceWith(new StopShooterCommandGroup()),
                    new IntakeBallCommandGroup().alongWith(new MagicAutoDrive(19)).withTimeout(5),
                    new TurnToAngle(-105, 0.5).raceWith(new StoreIntakeCommandGroup()),
                    new MagicAutoDrive(15).withTimeout(5),
                    new ShootBallCommandGroup().withTimeout(2),
                    new StopShooterCommandGroup().withTimeout(0.25)
                    );
    }

}
