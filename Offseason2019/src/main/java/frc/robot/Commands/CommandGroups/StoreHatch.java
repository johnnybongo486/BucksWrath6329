package frc.robot.Commands.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Commands.Wrist.*;
import frc.robot.Commands.Intake.*;
import frc.robot.Commands.Claw.*;


public class StoreHatch extends CommandGroup {

	public StoreHatch() {
        addParallel(new SetBooleanFalse(), 0.05);
        addParallel(new OpenClaw(), 0.1);
        addSequential(new ZeroWrist());
        addSequential(new HoldHatch());

    }
}