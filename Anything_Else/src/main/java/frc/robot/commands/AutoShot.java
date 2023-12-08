package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoShot extends SequentialCommandGroup {

	public AutoShot() {
        addCommands(new SetShooterVelocity(6400).alongWith(new SetUpperShooterVelocity(9600))); 
    }  
}