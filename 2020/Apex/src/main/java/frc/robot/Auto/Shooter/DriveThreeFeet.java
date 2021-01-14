package frc.robot.Auto.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Auto.Drivetrain.AutoDrive;
import frc.robot.Auto.Drivetrain.DriveStraight;
import frc.robot.Commands.Drivetrain.LowGear;

public class DriveThreeFeet extends CommandGroup {

	public DriveThreeFeet() {
        addSequential(new LowGear(), 0.01);
        //addSequential(new DriveStraight(3,1)); // distance, max speed

         addSequential(new AutoDrive(3, 100,100));  // distance, max velocity, max accel
       
    }
}