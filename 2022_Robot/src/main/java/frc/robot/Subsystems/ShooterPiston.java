package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterPiston extends SubsystemBase {

   private final Servo leftShooterPiston = new Servo(1);
   private final Servo rightShooterPiston = new Servo(2);

   public ShooterPiston(){
      
   }

   public void store() {
      leftShooterPiston.setAngle(45);
      rightShooterPiston.setAngle(45);
   }

   public void fullDeploy() {
      leftShooterPiston.setAngle(135);
      rightShooterPiston.setAngle(135);
   }

   public void zone1Deploy() {
      leftShooterPiston.setAngle(80);
      rightShooterPiston.setAngle(80);
   }

   public void zone2Deploy() {
      leftShooterPiston.setAngle(78);
      rightShooterPiston.setAngle(78);
   }

   public void zone3Deploy() {
      leftShooterPiston.setAngle(92);
      rightShooterPiston.setAngle(92);
   }

   public void zone4Deploy() {
      leftShooterPiston.setAngle(100);
      rightShooterPiston.setAngle(100);
   }
  
}
