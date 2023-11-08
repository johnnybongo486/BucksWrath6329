package frc.robot.auto;

import frc.robot.commands.Elevator.ReadyStateCommandGroup;
import frc.robot.commands.Intake.FloorIntakeCommandGroup;
import frc.robot.commands.Intake.ScoreObject;
import frc.robot.commands.Intake.StoreObject;
import frc.robot.commands.Intake.StoreObjectCommandGroup;
import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.commands.LEDs.SetCubeMode;
import frc.robot.commands.Wrist.DoNothingCommand;
import frc.robot.subsystems.Swerve;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ThreePieceHHMAuto extends SequentialCommandGroup {

    public ThreePieceHHMAuto(Swerve s_Swerve){
    
        PathPlannerTrajectory path1 = PathPlanner.loadPath("GetObject1", 4.0, 3.0);
        PathPlannerTrajectory path2 = PathPlanner.loadPath("ReturnCube1", 4.0, 3.0);
        PathPlannerTrajectory path3 = PathPlanner.loadPath("GetObject2", 4.0, 3.0);
        PathPlannerTrajectory path4 = PathPlanner.loadPath("ReturnCone2", 4.0, 3.0);


        addCommands(
            new AutoZero(s_Swerve).withTimeout(0.05).andThen(new AutoElevatorOut()).alongWith(new SetConeMode()).alongWith(new StoreObject()).withTimeout(0.2),
            new AutoScoreHighConeCommandGroup(),
            new SetCubeMode().withTimeout(0.05),
            s_Swerve.followTrajectoryCommand(path1, true).alongWith(new AutoHomeStateCommandGroup().andThen(new FloorIntakeCommandGroup().withTimeout(1.5))),
            s_Swerve.followTrajectoryCommand(path2, false).alongWith(new StoreObjectCommandGroup().withTimeout(1).andThen(new ReadyStateCommandGroup().andThen(new DoNothingCommand()).withTimeout(1).andThen(new AutoScoreHighCubeCommandGroup()))),  // was 2
            new SetConeMode().withTimeout(0.05),
            s_Swerve.followTrajectoryCommand(path3, false).alongWith(new AutoHomeStateCommandGroup().andThen(new FloorIntakeCommandGroup().withTimeout(1.5))),
            new StoreObjectCommandGroup().withTimeout(0.5)
            //s_Swerve.followTrajectoryCommand(path4, false).alongWith(new StoreObjectCommandGroup().withTimeout(0.5).andThen(new ReadyStateCommandGroup().andThen(new DoNothingCommand()).withTimeout(1.25).andThen(new ScoreObject().withTimeout(0.5)))),
            //new AutoHomeStateCommandGroup() 
        );
    }

}
