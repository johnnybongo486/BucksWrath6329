package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.commands.Intake.FloorIntakeCommandGroup;
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

public class BlueConeBackoffAuto extends SequentialCommandGroup {
    private double i2m = 0.0254;

    public BlueConeBackoffAuto(Swerve s_Swerve){
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
        Trajectory GrabBlueCube1 =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0 * i2m, 0 * i2m, new Rotation2d(Math.toRadians(180))),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(140 * i2m, 0 * i2m)),
                //End one meter forward
                new Pose2d(193 * i2m, 16 * i2m, new Rotation2d(Math.toRadians(0))),
                configRev);

        var thetaController =
            new ProfiledPIDController(
                Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                GrabBlueCube1,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);

        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(GrabBlueCube1.getInitialPose())),
            //new FloorIntakeCommandGroup().alongWith(swerveControllerCommand).withTimeout(5)
            swerveControllerCommand
        );
    }
}
