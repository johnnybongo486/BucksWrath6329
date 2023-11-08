package frc.robot.Auto.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Auto.Drivetrain.LimelightAuto;
import frc.robot.Auto.Drivetrain.NeoAutoDrive;
import frc.robot.Commands.Drivetrain.LowGear;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.IntakeStop;
import frc.robot.Commands.Serializer.stopSerializer;
import frc.robot.Commands.Shooter.SetShooterVelocity;
import frc.robot.Commands.Shooter.StopShooter;

public class Left5BallAuto extends CommandGroup {

    public Left5BallAuto() {

        addSequential(new LowGear(), 0.1);
        addParallel(new IntakeBallCommandGroup());
        addSequential(new NeoAutoDrive(-7, -7));
        addSequential(new IntakeStop());
        addSequential(new LimelightAuto());
        addSequential(new SetShooterVelocity(1950)); //change this number
        addParallel(new StopShooter(), 2);
        addParallel(new stopSerializer(), 0.1);

        
    }
}