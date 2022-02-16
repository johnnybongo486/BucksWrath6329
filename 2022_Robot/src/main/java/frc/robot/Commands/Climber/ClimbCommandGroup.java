package frc.robot.Commands.Climber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimbCommandGroup extends SequentialCommandGroup {

	public ClimbCommandGroup() {
        addCommands(new ClimberVertical().withTimeout(1), new GoToFullDownPosition().withTimeout(2));
    }  
}
