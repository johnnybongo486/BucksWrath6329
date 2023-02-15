package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class RedConeCubeAuto extends SequentialCommandGroup {
    private double i2m = 0.0254;

    public RedConeCubeAuto(Swerve s_Swerve){
        TrajectoryConfig configFwd =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics).setEndVelocity(0.5);

        TrajectoryConfig configRev1 =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);
        configRev1.setReversed(true);
        configRev1.setEndVelocity(0.5);

        TrajectoryConfig configRev2 =
            new TrajectoryConfig(
                    0.5,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);
        configRev2.setReversed(true);
        configRev2.setStartVelocity(0.5);
        configRev2.setEndVelocity(0.5);
        
        TrajectoryConfig configRev3 =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);
        configRev3.setReversed(true);
        configRev3.setStartVelocity(0.5);

        // An example trajectory to follow.  All units in meters.
        Trajectory BackupToCarrier =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0 * i2m, 0 * i2m, new Rotation2d(Math.toRadians(180))),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(31 * i2m, 0 * i2m)),
                //End one meter forward
                new Pose2d(62 * i2m, 0 * i2m, new Rotation2d(Math.toRadians(180))),
                configRev1);

        Trajectory BackOverCarrier =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(62 * i2m, 0 * i2m, new Rotation2d(Math.toRadians(180))),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(90 * i2m, 0 * i2m)),
                //End one meter forward
                new Pose2d(101 * i2m, 0 * i2m, new Rotation2d(Math.toRadians(180))),
                configRev2);

        Trajectory GrabCube =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(101 * i2m, 0 * i2m, new Rotation2d(Math.toRadians(180))),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(140 * i2m, -8 * i2m)),
                //End one meter forward
                new Pose2d(193 * i2m, -16 * i2m, new Rotation2d(Math.toRadians(0))),
                configRev3);

        var thetaController =
            new ProfiledPIDController(
                Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                BackupToCarrier,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);

        SwerveControllerCommand swerveControllerCommand2 =
            new SwerveControllerCommand(
                BackOverCarrier,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);

        SwerveControllerCommand swerveControllerCommand3 =
            new SwerveControllerCommand(
                GrabCube,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);


        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.1),
            new InstantCommand(() -> s_Swerve.resetOdometry(BackupToCarrier.getInitialPose())),
            swerveControllerCommand,
            new InstantCommand(() -> s_Swerve.resetOdometry(BackOverCarrier.getInitialPose())),
            swerveControllerCommand2,
            new InstantCommand(() -> s_Swerve.resetOdometry(GrabCube.getInitialPose())),
            swerveControllerCommand3
        );
    }
}
