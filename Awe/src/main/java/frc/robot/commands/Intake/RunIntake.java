package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RunIntake extends CommandBase {

    private double intakeAmps = 0;

    public RunIntake() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {

    }

    public void execute() {
        intakeAmps = RobotContainer.intake.getIntakeAmps();

        if (intakeAmps < 10) {  // may not work right
            RobotContainer.intake.intakeCube(); 
        }
        
        else {
            RobotContainer.intake.stopIntake();
        }
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotContainer.intake.holdCube();;
    }

    protected void interrupted() {
        end();
    }
}
