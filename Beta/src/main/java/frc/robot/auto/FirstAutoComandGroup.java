package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;

public class FirstAutoComandGroup extends SequentialCommandGroup {

    private Swerve s_swerve;
    
    public FirstAutoComandGroup() {
        addCommands(new DriveOneMeter(s_swerve), new Turn90Degrees(s_swerve));
    }

}
