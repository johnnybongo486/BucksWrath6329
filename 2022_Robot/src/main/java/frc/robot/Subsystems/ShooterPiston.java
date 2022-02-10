package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterPiston extends SubsystemBase {

   private final Servo leftShooterPiston = new Servo(2);
   private final Servo rightShooterPiston = new Servo(3);

   private double setAngle = 0;

   public ShooterPiston(){
      
   }

   public void store() {
      leftShooterPiston.setAngle(30);
      rightShooterPiston.setAngle(30);
   }

   public void fullDeploy() {
      leftShooterPiston.setAngle(135);
      rightShooterPiston.setAngle(135);
   }

   public void tarmacDeploy() {
      leftShooterPiston.setAngle(90);
      rightShooterPiston.setAngle(90);
   }

   public void lowgoalDeploy() {
      leftShooterPiston.setAngle(100);
      rightShooterPiston.setAngle(100);
   }

   public void setPosition(double angle) {
      this.setAngle = angle;
      leftShooterPiston.setAngle(setAngle);
      rightShooterPiston.setAngle(setAngle);
   }  
}
