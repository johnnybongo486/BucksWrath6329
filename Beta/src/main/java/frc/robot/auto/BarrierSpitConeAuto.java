package frc.robot.auto;

import frc.robot.commands.Intake.FloorIntakeCommandGroup;
import frc.robot.commands.Intake.SpitCube;
import frc.robot.commands.Intake.StoreObject;
import frc.robot.commands.Intake.StoreObjectCommandGroup;
import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.commands.LEDs.SetCubeMode;
import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class BarrierSpitConeAuto extends SequentialCommandGroup {

    public BarrierSpitConeAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("BarrierGrab1", 3.0, 2.0);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("BarrierSpit1", 4.0, 4.0);
        PathPlannerTrajectory path3 = PathPlanner.loadPath("BarrierGrab2", 4.0, 4.0);
        PathPlannerTrajectory path4 = PathPlanner.loadPath("BarrierSpit2", 4.0, 4.0);
        PathPlannerTrajectory path5 = PathPlanner.loadPath("BarrierConeGrab", 4.0, 4.0);

        
        addCommands(
            new AutoZero(s_Swerve).alongWith(new SetConeMode()).alongWith(new AutoElevatorOut()).alongWith(new StoreObject()).withTimeout(0.4),
            new AutoScoreHighConeCommandGroup(),
            new SetCubeMode().withTimeout(0.05),
            s_Swerve.followTrajectoryCommand(path1, true).alongWith(new CableAutoHomeStateCommandGroup().andThen(new FloorIntakeCommandGroup().withTimeout(1.5))),
            s_Swerve.followTrajectoryCommand(path2, false).alongWith(new StoreObjectCommandGroup().withTimeout(1).andThen(new SpitCube().withTimeout(0.5))),
            s_Swerve.followTrajectoryCommand(path3, false).alongWith(new FloorIntakeCommandGroup().withTimeout(1.75)),
            s_Swerve.followTrajectoryCommand(path4, false).alongWith(new StoreObjectCommandGroup().withTimeout(1).andThen(new SpitCube().withTimeout(0.5))),
            s_Swerve.followTrajectoryCommand(path5, false).alongWith(new SetConeMode().withTimeout(1).andThen(new FloorIntakeCommandGroup().withTimeout(1.5))),
            new StoreObjectCommandGroup().withTimeout(1)
        );
    }

}
