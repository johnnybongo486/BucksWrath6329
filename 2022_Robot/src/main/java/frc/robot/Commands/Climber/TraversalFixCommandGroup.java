package frc.robot.Commands.Climber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TraversalFixCommandGroup extends SequentialCommandGroup {

	public TraversalFixCommandGroup() {
        addCommands(
        new ClimberDeploy().withTimeout(0.05),
        new GoToClimbPosition().withTimeout(0.75),
        new ClimberVertical().withTimeout(0.25),
        new GoToFullDownPosition().withTimeout(2.25));
    }  
}
