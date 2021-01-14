package frc.robot.Auto.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.Shooter.*;
import frc.robot.Commands.Serializer.*;
import frc.robot.Commands.Intake.*;
import frc.robot.Auto.Drivetrain.LimelightAuto;
import frc.robot.Auto.Drivetrain.NeoAutoDrive;
import frc.robot.Auto.Drivetrain.TurnToAngle;
import frc.robot.Commands.Drivetrain.*;

public class Right5BallAuto extends CommandGroup {

    public Right5BallAuto() {
        addSequential(new LowGear(), 0.01);
        // addSequential(new DeployIntakePiston(), 0.05);
        // addParallel(new RunIntakeAuto(3));
        // addParallel(new intakeSerializerAuto(3));
        addSequential(new NeoAutoDrive(-5, -5));
        addSequential(new LimelightAuto(), 1.5);
        addSequential(new SetShooterVelocity(1950)); // change this number
        addSequential(new shooterRunSerializer(), 1.5);
        addSequential(new stopSerializer(), 0.1);
        addSequential(new TurnToAngle(5, 0.3));
        addSequential(new DeployIntakePiston(), 0.05);

        addParallel(new RunIntakeAuto(2));
        addParallel(new intakeSerializerAuto(2));
        addSequential(new NeoAutoDrive(-3.5, -3.5));
        addParallel(new RunIntakeAuto(2));
        addParallel(new intakeSerializerAuto(2));
        addSequential(new NeoAutoDrive(-3.5, -3.5));
        addParallel(new RunIntakeAuto(2));
        addParallel(new intakeSerializerAuto(2));
        addSequential(new NeoAutoDrive(-3.5, -3.5));
        addSequential(new LimelightAuto(), 1.5);
        addSequential(new SetShooterVelocity(2050)); // change this number
        addSequential(new shooterRunSerializer(), 1.5);
        addParallel(new stopSerializer(), 0.1);

        addSequential(new StopShooter());

        
    }
}