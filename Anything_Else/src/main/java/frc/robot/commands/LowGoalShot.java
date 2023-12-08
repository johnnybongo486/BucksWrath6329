package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LowGoalShot extends SequentialCommandGroup {

	public LowGoalShot() {
        addCommands(new SetShooterVelocity(6400).alongWith(new SetUpperShooterVelocity(9600))); 
    }  
}