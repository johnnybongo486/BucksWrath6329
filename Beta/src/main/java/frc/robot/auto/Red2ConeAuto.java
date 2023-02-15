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

public class Red2ConeAuto extends SequentialCommandGroup {
    private double i2m = 0.0254;

    public Red2ConeAuto(Swerve s_Swerve){
        TrajectoryConfig configFwd =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);

        TrajectoryConfig configRev =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);
        configRev.setReversed(true);
        // An example trajectory to follow.  All units in meters.

        Trajectory GrabRedCone1 =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0 * i2m, 0 * i2m, new Rotation2d(Math.toRadians(180))),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(98 * i2m, 12 * i2m)),
                //End one meter forward
                new Pose2d(193 * i2m, 16 * i2m, new Rotation2d(Math.toRadians(0))),
                configRev);

        Trajectory ReturnRedCone1 =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(193 * i2m, 16 * i2m, new Rotation2d(Math.toRadians(0))),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(43 * i2m, 16 * i2m)),
                //End one meter forward
                new Pose2d(0 * i2m, 44 * i2m, new Rotation2d(Math.toRadians(180))),
                configRev);

        var thetaController =
            new ProfiledPIDController(
                Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                GrabRedCone1,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);

        SwerveControllerCommand swerveControllerCommand2 =
            new SwerveControllerCommand(
                ReturnRedCone1,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);

        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.01),
            new InstantCommand(() -> s_Swerve.resetOdometry(GrabRedCone1.getInitialPose())),
            swerveControllerCommand,
            new InstantCommand(() -> s_Swerve.resetOdometry(ReturnRedCone1.getInitialPose())),
            swerveControllerCommand2
        );
    }
}
