package frc.robot.auto;

import frc.robot.commands.Intake.StoreObject;
import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CenterBalanceAuto extends SequentialCommandGroup {

    public CenterBalanceAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("CenterBalance1", 1.0, 1.0);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("CenterBalance2", 1.0, 1.0);

        addCommands(
            new AutoZero(s_Swerve).alongWith(new SetConeMode()).alongWith(new AutoElevatorOut()).alongWith(new StoreObject()).withTimeout(0.4),
            new AutoScoreHighConeCommandGroup(),
            new AutoHomeStateCommandGroup(),
            s_Swerve.followTrajectoryCommand(path1, true),
            s_Swerve.followTrajectoryCommand(path2, false),
            new AutoBalance(s_Swerve).withTimeout(5)
        );
    }

}
