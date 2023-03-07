package frc.robot.auto;

import frc.robot.commands.Elevator.ReadyStateCommandGroup;
import frc.robot.commands.Intake.FloorIntakeCommandGroup;
import frc.robot.commands.Intake.StoreObjectCommandGroup;
import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoConeBalanceAuto extends SequentialCommandGroup {

    public TwoConeBalanceAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("GetObject1", 4.0, 3.0);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("ReturnCone1", 4.0, 3.0);
        PathPlannerTrajectory path3 = PathPlanner.loadPath("ConeBalance", 4.0, 3.0);

        addCommands(
            new AutoZero(s_Swerve).alongWith(new SetConeMode()).alongWith(new AutoElevatorOut()).withTimeout(0.5),
            new AutoScoreHighConeCommandGroup(),
            s_Swerve.followTrajectoryCommand(path1, true).alongWith(new AutoHomeStateCommandGroup().andThen(new FloorIntakeCommandGroup().withTimeout(2))),
            s_Swerve.followTrajectoryCommand(path2, false).alongWith(new StoreObjectCommandGroup().withTimeout(2).andThen(new ReadyStateCommandGroup())),
            new AutoScoreHighConeCommandGroup(),
            s_Swerve.followTrajectoryCommand(path3, false).alongWith(new AutoHomeStateCommandGroup()),
            new AutoBalance(s_Swerve).withTimeout(3)
        );
    }

}
