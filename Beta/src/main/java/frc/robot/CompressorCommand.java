package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class CompressorCommand extends CommandBase {

    boolean pressureSwitch;

    public CompressorCommand() {
        addRequirements(RobotContainer.airCompressor);
    }

    public void initialize() {

    }

    public void execute() {
        pressureSwitch = RobotContainer.airCompressor.pressureCheck();
        RobotContainer.airCompressor.runCompressor();
    }

    public boolean isFinished() {
        return pressureSwitch = false;
    }

    protected void end() {
        RobotContainer.airCompressor.stopCompressor();
    }

    protected void interrupted() {
        
    }
}
