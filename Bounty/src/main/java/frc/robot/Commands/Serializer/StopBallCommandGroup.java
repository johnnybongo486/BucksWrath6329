package frc.robot.Commands.Serializer;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Shooter.StopShooter;

public class StopBallCommandGroup extends CommandGroup {

	public StopBallCommandGroup() {
        addSequential(new stopSerializer(), 0.1);
        addSequential(new stopHopper(), 0.1);
        addSequential(new StopShooter(), 3);
    }
    
}