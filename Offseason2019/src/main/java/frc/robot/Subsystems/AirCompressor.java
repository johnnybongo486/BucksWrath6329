package frc.robot.Subsystems;

import frc.robot.Commands.CompressorCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;

public class AirCompressor extends Subsystem {
   Compressor c = new Compressor(0);

   public void initDefaultCommand() {
       setDefaultCommand(new CompressorCommand());
   }

   public boolean pressureCheck() {
       boolean pressureSwitch = c.getPressureSwitchValue();
       return pressureSwitch;
   }

   public void runCompressor() {
       c.setClosedLoopControl(true);
   }

   public void stopCompressor() {
       c.setClosedLoopControl(false);
   }
}
