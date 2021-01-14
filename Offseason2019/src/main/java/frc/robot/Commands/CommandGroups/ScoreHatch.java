package frc.robot.Commands.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Commands.Intake.*;
import frc.robot.Commands.Claw.*;
import frc.robot.Commands.Wrist.*;


public class ScoreHatch extends CommandGroup {

	public ScoreHatch() {
        addParallel(new SetBooleanFalse(), 0.05);
        addParallel(new CloseClaw());
        addSequential(new ShootHatch());

    }
}