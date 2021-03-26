package frc.robot.Commands.Auto;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class BallFinderAuto extends CommandGroup{
    
    public BallFinderAuto() {
            addSequential(new AutoFindTarget());
            // turn on the intake now
            if (Robot.Drivetrain.getIsRed() == true) {
                addSequential(new TurnToAngle(-45, 0.3));
                addSequential(new AutoVisionIntake());
                addSequential(new TurnToAngle(45, 0.3));
                addSequential(new AutoVisionIntake());
                addSequential(new TurnToAngle(-45, 0.3));
                addSequential(new AutoVisionIntake());
                addSequential(new TurnToAngle(0, 0.3));
            }
            else {
                addSequential(new TurnToAngle(45, 0.3));
                addSequential(new AutoVisionIntake());
                addSequential(new TurnToAngle(-45, 0.3));
                addSequential(new AutoVisionIntake());
                addSequential(new TurnToAngle(45, 0.3));
                addSequential(new AutoVisionIntake());
                addSequential(new TurnToAngle(0, 0.3));
            }
        }
    }
