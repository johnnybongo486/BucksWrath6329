package frc.robot.auto;

import frc.robot.commands.Intake.FloorIntakeCommandGroup;
import frc.robot.commands.Intake.StoreObjectCommandGroup;
import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.commands.LEDs.SetCubeMode;
import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class BarrierGrabBalanceAuto extends SequentialCommandGroup {

    public BarrierGrabBalanceAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("BarrierGrab1", 3.0, 3.0);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("GrabBalance1", 4.0, 3.0);
        
        addCommands(
            new AutoZero(s_Swerve).alongWith(new SetConeMode()).alongWith(new AutoElevatorOut()).withTimeout(0.4),
            new AutoScoreHighConeCommandGroup(),
            new SetCubeMode().withTimeout(0.05),
            s_Swerve.followTrajectoryCommand(path1, true).alongWith(new AutoHomeStateCommandGroup().andThen(new FloorIntakeCommandGroup().withTimeout(2.5))),
            s_Swerve.followTrajectoryCommand(path2, false).alongWith(new StoreObjectCommandGroup().withTimeout(3)),
            new AutoBackBalance(s_Swerve).withTimeout(4)
        );
    }

}
