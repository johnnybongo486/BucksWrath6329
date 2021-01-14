package frc.robot.Auto.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Auto.Drivetrain.NeoAutoDrive;
import frc.robot.Commands.Drivetrain.LowGear;
import frc.robot.Commands.Serializer.shooterRunSerializer;
import frc.robot.Commands.Serializer.stopSerializer;
import frc.robot.Commands.Shooter.SetShooterVelocity;
import frc.robot.Commands.Shooter.StopShooter;

public class CenterAuto extends CommandGroup {

    public CenterAuto() {
        
        addParallel(new LowGear(), 0.01);
        addSequential(new SetShooterVelocity(1950));// 2445
        addSequential(new NeoAutoDrive (-4, -4));
        addSequential(new shooterRunSerializer(), 1.5);
        addParallel(new StopShooter(), 2);
        addParallel(new stopSerializer(), 0.1);
        
    }
}