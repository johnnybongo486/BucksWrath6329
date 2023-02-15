package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class IntakeObject extends CommandBase {

    private boolean isCone;
    private double upperAmps = 0;
    private double lowerAmps = 0;

    public IntakeObject() {
        addRequirements(RobotContainer.intake);
    }

    public void initialize() {
        isCone = RobotContainer.candleSubsystem.getIsCone();
    }

    public void execute() {
        upperAmps = RobotContainer.intake.getUpperIntakeAmps();
        lowerAmps = RobotContainer.intake.getLowerIntakeAmps();

        if (isCone == true){
            RobotContainer.intake.intakeCone();
            
            if (upperAmps > 5 || lowerAmps > 5) {
                RobotContainer.candleSubsystem.setAnimate("Strobe Yellow");
            }

            else{
                RobotContainer.candleSubsystem.setAnimate("Yellow");
            }
        }

        else {
            RobotContainer.intake.intakeCube();

            if (upperAmps > 5 || lowerAmps > 5) {
                RobotContainer.candleSubsystem.setAnimate("Strobe Purple");
            }

            else{
                RobotContainer.candleSubsystem.setAnimate("Purple");
            }
        }
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        if (isCone == true){
            RobotContainer.intake.holdCone();
        }

        else {
            RobotContainer.intake.holdCube();;
        }
    }

    protected void interrupted() {
        end();
    }
}