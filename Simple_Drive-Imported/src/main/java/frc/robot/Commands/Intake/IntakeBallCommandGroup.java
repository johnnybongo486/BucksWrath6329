package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeBallCommandGroup extends SequentialCommandGroup {

	public IntakeBallCommandGroup() {
        addCommands(new IntakeDeploy().withTimeout(0.25), new RunIntake(), new RunCenterIntake());
    }  
}
