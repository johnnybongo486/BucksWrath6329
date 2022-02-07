package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
    Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH);

    public AirCompressor(){

    }
    
    public boolean pressureCheck() {
        boolean pressureSwitch = compressor.getPressureSwitchValue();
        return pressureSwitch;
    }

    public void runCompressor() {
        compressor.enableDigital();
    }

    public void stopCompressor() {
        compressor.disable();
    }
}
