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

public class BarrierConeCubeAuto extends SequentialCommandGroup {

    public BarrierConeCubeAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("BarrierGrab1", 1.5, 1.5);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("BarrierReturnCube1", 1.5, 1.5);
        PathPlannerTrajectory path3 = PathPlanner.loadPath("BarrierExit", 3.0, 2.0);
        
        addCommands(
            new AutoZero(s_Swerve).alongWith(new SetConeMode()).alongWith(new AutoElevatorOut()).alongWith(new StoreObject()).withTimeout(0.4),
            new AutoScoreHighConeCommandGroup(),
            new SetCubeMode().withTimeout(0.05),
            s_Swerve.followTrajectoryCommand(path1, true).alongWith(new CableAutoHomeStateCommandGroup().andThen(new FloorIntakeCommandGroup().withTimeout(1.8))),
            s_Swerve.followTrajectoryCommand(path2, false).alongWith(new StoreObjectCommandGroup().withTimeout(2).andThen(new ReadyStateCommandGroup())),
            new AutoScoreHighCubeCommandGroup(),
            s_Swerve.followTrajectoryCommand(path3, false).alongWith(new CableAutoHomeStateCommandGroup())
            //.andThen(new FloorIntakeCommandGroup().withTimeout(1.5))),
            //new StoreObjectCommandGroup().withTimeout(1)
        );
    }

}
