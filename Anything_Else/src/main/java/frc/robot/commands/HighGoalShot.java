package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class HighGoalShot extends SequentialCommandGroup {

	public HighGoalShot() {
        addCommands(new SetShooterVelocity(6400).alongWith(new SetUpperShooterVelocity(9600))); 
    }  
}