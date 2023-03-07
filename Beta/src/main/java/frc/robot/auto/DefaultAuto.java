package frc.robot.auto;

import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DefaultAuto extends SequentialCommandGroup {

    public DefaultAuto(Swerve s_Swerve){

        addCommands(
            new AutoZero(s_Swerve).alongWith(new SetConeMode()).alongWith(new AutoElevatorOut()).withTimeout(0.4),
            new AutoScoreHighConeCommandGroup(),
            new AutoHomeStateCommandGroup()
        );
    }

}
