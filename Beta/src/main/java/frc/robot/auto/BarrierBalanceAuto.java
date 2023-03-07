package frc.robot.auto;

import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class BarrierBalanceAuto extends SequentialCommandGroup {

    public BarrierBalanceAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("BarrierBalance", 4.0, 3.0);
        
        addCommands(
            new AutoZero(s_Swerve).alongWith(new SetConeMode()).alongWith(new AutoElevatorOut()).withTimeout(0.5),
            new AutoScoreHighConeCommandGroup(),
            new AutoHomeStateCommandGroup(),
            s_Swerve.followTrajectoryCommand(path1, true),
            new AutoBalance(s_Swerve).withTimeout(5)
        );
    }

}
