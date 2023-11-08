package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Elevator.GoToDoubleHPElevatorPosition;
import frc.robot.commands.Intake.IntakeObject;
import frc.robot.commands.Wrist.GoToDoubleHPWristPosition;

public class DoubleHPCommandGroup extends SequentialCommandGroup {
    
    public DoubleHPCommandGroup() {
        addCommands(new GoToDoubleHPElevatorPosition().alongWith(new GoToDoubleHPWristPosition()).withTimeout(2), new IntakeObject());
    }

}
