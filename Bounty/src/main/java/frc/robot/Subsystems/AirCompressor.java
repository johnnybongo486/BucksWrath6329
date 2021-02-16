package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Commands.Drivetrain.CompressorCommand;

public class AirCompressor extends Subsystem {
    Compressor compressor = new Compressor(0);

    public void initDefaultCommand() {
        setDefaultCommand(new CompressorCommand());
    }

    public boolean pressureCheck() {
        boolean pressureSwitch = compressor.getPressureSwitchValue();
        return pressureSwitch;
    }

    public void runCompressor() {
        compressor.setClosedLoopControl(true);
    }

    public void stopCompressor() {
        compressor.setClosedLoopControl(false);
    }
}