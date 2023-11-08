package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Intake.ReverseIntake;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.commands.Shooter.SetLowerShooterVelocity;
import frc.robot.commands.Shooter.SetUpperShooterVelocity;

public class ChuckItCommandGroup extends SequentialCommandGroup {
    
    public ChuckItCommandGroup() {
        addCommands(new ReverseIntake().withTimeout(0.1)
            .andThen(new StopIntake().withTimeout(0.2))
            .andThen(new SetUpperShooterVelocity(16000)
            .alongWith(new SetLowerShooterVelocity(16000))));
    }

}