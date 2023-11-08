package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoDrive;

public class DriveThreeFeet extends SequentialCommandGroup{
   
    public DriveThreeFeet() {
        addCommands(
        new MagicAutoDrive(3, 0.091));
    }

}
