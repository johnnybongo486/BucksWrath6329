package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Intake.ReverseIntake;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.commands.Shooter.SetLowerShooterVelocity;
import frc.robot.commands.Shooter.SetUpperShooterVelocity;

public class HighShotCommandGroup extends SequentialCommandGroup {
    
    public HighShotCommandGroup() {
        addCommands(new ReverseIntake().withTimeout(0.1)
            .andThen(new StopIntake().withTimeout(0.2))
            .andThen(new SetUpperShooterVelocity(5000)
            .alongWith(new SetLowerShooterVelocity(10000))));
    }

}