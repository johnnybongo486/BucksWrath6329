package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Downshift extends CommandGroup {

    public Downshift() {
        addParallel(new RumbleLeft());
        addSequential(new LowGear());
    }
}