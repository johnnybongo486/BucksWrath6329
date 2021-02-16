package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveThreeFeet extends CommandGroup {
    
    public DriveThreeFeet() {
        addSequential(new MagicAutoDrive(3));
    }
}
