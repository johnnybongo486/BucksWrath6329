package frc.robot.Commands.Climber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TraversalClimbCommandGroup extends SequentialCommandGroup {

	public TraversalClimbCommandGroup() {
        addCommands(
        new GoToClimbPosition().withTimeout(2).alongWith(new ClimberDeploy().withTimeout(0.05)),
        new GoToPullUpPosition().withTimeout(0.5),
        new ClimberVertical().withTimeout(0.01)
        );
    }  
}
