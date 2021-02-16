package frc.robot.Commands.Drivetrain.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.paths.DriveThreeFeetArc;
import frc.robot.Commands.Drivetrain.FollowPath;

public class DriveThreeFeet extends CommandGroup{
    public DriveThreeFeet() {
        addSequential(new FollowPath(new DriveThreeFeetArc()));
    }
}
