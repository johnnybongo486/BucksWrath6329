package frc.robot.auto;

import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class BalanceTest extends SequentialCommandGroup {
    public BalanceTest(Swerve s_Swerve){
            
        PathPlannerTrajectory path1 = PathPlanner.loadPath("BalanceTest", 3.0, 3.0);

        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.1),
            s_Swerve.followTrajectoryCommand(path1, true),
            new AutoBalance(s_Swerve).withTimeout(10)
        );
    }
}

