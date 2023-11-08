package frc.robot.Auto.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Auto.Drivetrain.LimelightAuto;
import frc.robot.Auto.Drivetrain.NeoAutoDrive;
import frc.robot.Commands.Drivetrain.LowGear;
import frc.robot.Commands.Serializer.stopSerializer;
import frc.robot.Commands.Shooter.SetShooterVelocity;
import frc.robot.Commands.Shooter.StopShooter;

public class LeftAuto extends CommandGroup {

    public LeftAuto() {

        addSequential(new LowGear(), 0.1);
        addSequential(new NeoAutoDrive(-3, -3));
        addSequential(new LimelightAuto());
        addSequential(new SetShooterVelocity(1950)); //change this number
        addParallel(new StopShooter(), 2);
        addParallel(new stopSerializer(), 0.1);
    }
}