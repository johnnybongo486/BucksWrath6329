package frc.robot.Auto.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TurnNinetyDegrees extends CommandGroup {

	public TurnNinetyDegrees() {
       addSequential(new TurnToAngle(90, 0.3));
    }
}