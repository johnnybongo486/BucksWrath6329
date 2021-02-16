package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBallCommandGroup extends CommandGroup {

	public ShootBallCommandGroup() {
        addSequential(new runSerializer(), 0.25);
        addSequential(new runHopper());
        //addParallel(new ReverseIntake(), 0.25); for 5 balls only
    }
}