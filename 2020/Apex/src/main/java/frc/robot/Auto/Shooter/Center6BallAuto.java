package frc.robot.Auto.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Auto.Drivetrain.LimelightAuto;
import frc.robot.Auto.Drivetrain.NeoAutoDrive;
import frc.robot.Auto.Drivetrain.TurnToAngle;
import frc.robot.Commands.Drivetrain.LowGear;
import frc.robot.Commands.Intake.DeployIntakePiston;
import frc.robot.Commands.Intake.RunIntakeAuto;
import frc.robot.Commands.Serializer.intakeSerializerAuto;
import frc.robot.Commands.Serializer.shooterRunSerializer;
import frc.robot.Commands.Serializer.stopSerializer;
import frc.robot.Commands.Shooter.SetShooterVelocity;
import frc.robot.Commands.Shooter.StopShooter;

public class Center6BallAuto extends CommandGroup {

    public Center6BallAuto() {

        addSequential(new LowGear(), 0.1);
      //  addSequential(new SetShooterVelocity(2100));// 2445
        //addSequential(new shooterRunSerializer(), 1.5);
        addSequential(new TurnToAngle(35, 0.3));
        //addParallel(new StopShooter(), 2);
        //addParallel(new stopSerializer(), 0.1);
        addSequential(new NeoAutoDrive (-8.1, -8.1));
        addSequential(new TurnToAngle(1, 0.3));
        addSequential(new DeployIntakePiston(), 0.05);
        addParallel(new RunIntakeAuto(1.5));
        addParallel(new intakeSerializerAuto(1.5));      
        addSequential(new NeoAutoDrive(-3.5, -3.5));
        addParallel(new RunIntakeAuto(1.5));
        addParallel(new intakeSerializerAuto(1.5));      
        addSequential(new NeoAutoDrive(-3.51, -3.51)); 
        addSequential(new LimelightAuto());
        addSequential(new SetShooterVelocity(2300)); //change this number
        addSequential(new shooterRunSerializer(), 1.5);
        addParallel(new stopSerializer(), 0.1);
        addSequential(new TurnToAngle(-5, 0.3));     
        addParallel(new RunIntakeAuto(1.5));
        addParallel(new intakeSerializerAuto(1.5)); 
        addSequential(new NeoAutoDrive(-4, -4));
        addSequential(new LimelightAuto());
        addSequential(new shooterRunSerializer(), 1.5);
        addParallel(new StopShooter(), 2);
        addParallel(new stopSerializer(), 0.1);

        
    }
}