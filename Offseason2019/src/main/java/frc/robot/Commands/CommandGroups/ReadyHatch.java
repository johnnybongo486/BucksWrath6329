package frc.robot.Commands.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Commands.Wrist.*;
import frc.robot.Commands.Intake.*;
import frc.robot.Commands.Claw.*;


public class ReadyHatch extends CommandGroup {

	public ReadyHatch() {
        addParallel(new SetBooleanTrue(), 0.05);
        addSequential(new OpenClaw(), 0.1);
        addParallel(new HoldHatch());
        addSequential(new GoToHatchPosition());
        addSequential(new OpenClaw());

    }
}