package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class UpShift extends CommandGroup {

    public UpShift() {
        addParallel(new RumbleRight());
        addSequential(new HighGear(), 0.05);
    }
}