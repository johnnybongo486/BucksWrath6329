package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Turn90Degrees extends CommandGroup {
    
    public Turn90Degrees() {
        addSequential(new TurnToAngle(90, 0.3));
    }
}
