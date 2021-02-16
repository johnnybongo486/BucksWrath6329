package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeBallCommandGroup extends CommandGroup {

	public IntakeBallCommandGroup() {
        addSequential(new DeployIntakePiston(), 0.05);
        addParallel(new RunIntake());
    }
}