package frc.robot.Commands.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Commands.Intake.*;
import frc.robot.Commands.Claw.*;


public class ScoreCargo extends CommandGroup {

	public ScoreCargo() {

        addParallel(new OpenClaw());
        addSequential(new ShootCargo());

    }
}