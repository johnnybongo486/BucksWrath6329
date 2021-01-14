package frc.robot.Commands.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Commands.Wrist.*;
import frc.robot.Commands.Intake.*;
import frc.robot.Commands.Claw.*;


public class PickupHatch extends CommandGroup {

	public PickupHatch() {

        addParallel(new SetBooleanTrue(), 0.05);
        addSequential(new GoToHatchPosition());
        addParallel(new CloseClaw(), 0.1);
        addSequential(new IntakeHatch());

    }
}