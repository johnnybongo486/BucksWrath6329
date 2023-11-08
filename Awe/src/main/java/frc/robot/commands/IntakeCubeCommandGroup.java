package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Wrist.GoToFloorIntakePosition;
import frc.robot.commands.Intake.RunIntake;

public class IntakeCubeCommandGroup extends SequentialCommandGroup {
    
    public IntakeCubeCommandGroup() {
        addCommands(new RunIntake().alongWith(new GoToFloorIntakePosition()));
    }

}