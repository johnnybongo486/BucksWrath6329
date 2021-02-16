package frc.robot.Commands.Intake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ReverseIntakeCommandGroup extends CommandGroup {

	public ReverseIntakeCommandGroup() {
        addSequential(new DeployIntakePiston(), 0.05);
        addParallel(new ReverseIntake());
        
        
		
  
    }
}