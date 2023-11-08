package frc.robot.auto;

import frc.robot.commands.Elevator.ReadyStateCommandGroup;
import frc.robot.commands.Intake.FloorIntakeCommandGroup;
import frc.robot.commands.Intake.StoreObject;
import frc.robot.commands.Intake.StoreObjectCommandGroup;
import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.commands.LEDs.SetCubeMode;
import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ConeCubeBalanceAuto extends SequentialCommandGroup {

    public ConeCubeBalanceAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("GetObject1", 4.0, 3.0);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("ReturnCube1", 4.0, 4.0);
        PathPlannerTrajectory path3 = PathPlanner.loadPath("CubeBalance", 4.0, 3.0);

        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.05).andThen(new AutoElevatorOut()).alongWith(new SetConeMode()).alongWith(new StoreObject()).withTimeout(0.2),
            new AutoScoreHighConeCommandGroup(),
            new SetCubeMode().withTimeout(0.05),
            s_Swerve.followTrajectoryCommand(path1, true).alongWith(new AutoHomeStateCommandGroup().andThen(new FloorIntakeCommandGroup().withTimeout(1.8))),
            s_Swerve.followTrajectoryCommand(path2, false).alongWith(new StoreObjectCommandGroup().withTimeout(1).andThen(new ReadyStateCommandGroup())),
            new AutoScoreHighCubeCommandGroup(),
            s_Swerve.followTrajectoryCommand(path3, false).alongWith(new AutoHomeStateCommandGroup()),
            new AutoBalance(s_Swerve).withTimeout(4)
        );
    }

}
