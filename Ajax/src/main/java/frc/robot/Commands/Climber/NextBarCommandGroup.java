package frc.robot.Commands.Climber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class NextBarCommandGroup extends SequentialCommandGroup {

	public NextBarCommandGroup() {
        addCommands(
        new GoToClimbPosition().withTimeout(2).alongWith(new ClimberDeploy().withTimeout(0.05))
        );
    }  
}
