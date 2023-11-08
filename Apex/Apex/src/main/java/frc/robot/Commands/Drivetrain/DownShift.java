package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DownShift extends CommandGroup {

    public DownShift() {
        addParallel(new RumbleLeft());
        addSequential(new LowGear(), 0.05);
    }
}