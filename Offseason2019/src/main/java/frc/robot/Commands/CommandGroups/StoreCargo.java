package frc.robot.Commands.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Commands.Wrist.*;
import frc.robot.Commands.Intake.*;
import frc.robot.Commands.Claw.*;


public class StoreCargo extends CommandGroup {

	public StoreCargo() {

        addSequential(new CloseClaw(), 0.1);
        addSequential(new ZeroWrist());
        addParallel(new HoldCargo());

    }
}