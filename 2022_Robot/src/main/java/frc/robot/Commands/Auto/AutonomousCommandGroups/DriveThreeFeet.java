package frc.robot.Commands.Auto.AutonomousCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Auto.MagicAutoDrive;
import frc.robot.Commands.Drivetrain.HighGear;

public class DriveThreeFeet extends SequentialCommandGroup{
   
    public DriveThreeFeet() {
        addCommands(
        new HighGear().withTimeout(0.05)  ,  
        new MagicAutoDrive(3, 0.11));
    }

}
