package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.GoToSingleHPElevatorPosition;
import frc.robot.commands.Intake.IntakeObject;
import frc.robot.commands.Wrist.GoToSingleHPWristPosition;

public class SingleHPCommandGroup extends SequentialCommandGroup {
    
    public SingleHPCommandGroup() {
        addCommands(new GoToSingleHPElevatorPosition().alongWith(new GoToSingleHPWristPosition()).withTimeout(2), new IntakeObject());
    }

}
