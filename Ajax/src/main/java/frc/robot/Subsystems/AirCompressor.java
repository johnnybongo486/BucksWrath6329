package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
    Compressor compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);

    public AirCompressor(){

    }
    
    public boolean pressureCheck() {
        boolean pressureSwitch = compressor.getPressureSwitchValue();
        return pressureSwitch;
    }

    public void runCompressor() {
        compressor.enabled();
    }

    public void stopCompressor() {
        compressor.disable();
    }
}
