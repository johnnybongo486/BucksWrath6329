package frc.robot.Auto.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Shooter.SetShooterVelocity;
import frc.robot.Commands.Shooter.*;
import frc.robot.Commands.Serializer.*;
import frc.robot.Auto.Drivetrain.LimelightAuto;
import frc.robot.Auto.Drivetrain.NeoAutoDrive;
import frc.robot.Commands.Drivetrain.*;

public class RightAuto extends CommandGroup {

    public RightAuto() {
        addSequential(new LowGear(), 0.01);
        addSequential(new NeoAutoDrive(-3, -3));
        addSequential(new LimelightAuto());
        addSequential(new SetShooterVelocity(1950)); //change this number
        addSequential(new shooterRunSerializer(), 1.5);
        addParallel(new StopShooter(), 2);
        addParallel(new stopSerializer(), 0.1);

        
    }
}