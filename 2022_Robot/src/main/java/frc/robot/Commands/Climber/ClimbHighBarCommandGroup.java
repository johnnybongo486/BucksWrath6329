package frc.robot.Commands.Climber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimbHighBarCommandGroup extends SequentialCommandGroup {

	public ClimbHighBarCommandGroup() {
        addCommands(new ClimberVertical().withTimeout(.25), new GoToFullDownPosition().withTimeout(2.5),
        new ClimberDeploy().withTimeout(0.01), 
        new GoToNextBarPosition().withTimeout(2.25),
        new ClimberVertical().withTimeout(0.5), 
        new GoToFullDownPosition().withTimeout(3)
        );

    }  
}
