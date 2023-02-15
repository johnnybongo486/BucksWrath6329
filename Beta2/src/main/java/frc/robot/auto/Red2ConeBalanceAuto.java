package frc.robot.auto;

import frc.robot.subsystems.Swerve;


import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class Red2ConeBalanceAuto extends SequentialCommandGroup {

    public Red2ConeBalanceAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("GetCone1", 4.5, 3.0);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("ReturnCone1", 4.5, 3.0);
        


        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.1),
            s_Swerve.followTrajectoryCommand(path1, true),
            s_Swerve.followTrajectoryCommand(path2, false)
        );
    }

}
        