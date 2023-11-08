package frc.robot.Commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CompressorCommand extends Command {

    boolean pressureSwitch;

    public CompressorCommand() {
        requires(Robot.AirCompressor);
    }

    protected void initialize() {

    }

    protected void execute() {
        pressureSwitch = Robot.AirCompressor.pressureCheck();
        Robot.AirCompressor.runCompressor();
    }

    protected boolean isFinished() {
        return pressureSwitch = false;
    }

    protected void end() {
        Robot.AirCompressor.stopCompressor();
    }

    protected void interrupted() {
        
    }
}