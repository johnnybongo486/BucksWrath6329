package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterPiston extends SubsystemBase {

   private final Solenoid shooterSol = new Solenoid(0, PneumaticsModuleType.CTREPCM, 2);

   public ShooterPiston(){
      
   }

   public void store() {
      shooterSol.set(true);
   }

   public void deploy() {
      shooterSol.set(false);
   }
}
