package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class AutoBackBalance extends CommandBase {

    private boolean isBalanced;
    private Swerve s_swerve;

    public AutoBackBalance(Swerve s_swerve) {
        this.s_swerve = s_swerve;
        addRequirements(s_swerve);
    }

    public void initialize() {
        isBalanced = s_swerve.isRobotBalanced();
    }

    public void execute() {
        isBalanced = s_swerve.isRobotBalanced();
        if (isBalanced == false){
            s_swerve.AutoBackBalance();
        }

        else {
            s_swerve.stopDrive();
        }
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
        s_swerve.stopDrive();
    }

    protected void interrupted() {
        end();
    }
}