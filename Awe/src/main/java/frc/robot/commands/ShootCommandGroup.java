package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Intake.RunIntake;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.commands.Shooter.SetLowerShooterVelocity;
import frc.robot.commands.Shooter.SetUpperShooterVelocity;

public class ShootCommandGroup extends SequentialCommandGroup {
    
    public ShootCommandGroup() {
        addCommands(new RunIntake().withTimeout(1)
            .andThen(new StopIntake().withTimeout(0.1))
            .andThen(new SetUpperShooterVelocity(0)
            .alongWith(new SetLowerShooterVelocity(1000))));
    }

}