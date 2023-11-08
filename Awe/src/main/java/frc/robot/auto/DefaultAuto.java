package frc.robot.auto;

import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DefaultAuto extends SequentialCommandGroup {

    public DefaultAuto(Swerve s_Swerve){

        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.25),
            new AutoScoreLowCommandGroup()
        );
    }

}
