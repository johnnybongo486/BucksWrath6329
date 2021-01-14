package frc.robot.Commands.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Commands.Wrist.*;
import frc.robot.Commands.Intake.*;
import frc.robot.Commands.Claw.*;


public class PickupCargo extends CommandGroup {

	public PickupCargo() {
        addParallel(new OpenClaw(), 0.1);
        addSequential(new GoToIntakePosition());
        addSequential(new IntakeCargo());

    }
}