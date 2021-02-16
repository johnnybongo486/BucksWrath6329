package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DPDriveThreeFeet extends CommandGroup {
    
    public DPDriveThreeFeet() {
        addSequential(new PositionAutoDrive(3, 3));
    }
}
