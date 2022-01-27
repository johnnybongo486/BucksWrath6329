package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StoreIntakeCommandGroup extends SequentialCommandGroup {

	public StoreIntakeCommandGroup() {
        addCommands(new IntakeRetract(), new StopIntake());
    }  
}
