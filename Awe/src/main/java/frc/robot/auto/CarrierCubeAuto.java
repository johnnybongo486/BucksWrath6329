package frc.robot.auto;

import frc.robot.commands.IntakeCubeCommandGroup;
import frc.robot.commands.StoreIntakeCommandGroup;
import frc.robot.subsystems.Swerve;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CarrierCubeAuto extends SequentialCommandGroup {

    public CarrierCubeAuto(Swerve s_Swerve){

        PathPlannerTrajectory path1 = PathPlanner.loadPath("CarrierSide", 1.5, 1.5);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("CarrierSide2", 3, 3);



        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.25),
            new AutoScoreMidCommandGroup(),
            s_Swerve.followTrajectoryCommand(path1, true).alongWith(new IntakeCubeCommandGroup().withTimeout(5)),
            s_Swerve.followTrajectoryCommand(path2, false).alongWith(new StoreIntakeCommandGroup().withTimeout(1.5)),
            new AutoScoreHighCommandGroup()
        );
    }

}
