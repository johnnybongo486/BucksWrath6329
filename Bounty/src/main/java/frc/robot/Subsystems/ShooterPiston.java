package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterPiston extends Subsystem {

   private final Servo leftShooterPiston = new Servo(1);
   private final Servo rightShooterPiston = new Servo(2);

   public ShooterPiston(){
      
   }

   public void initDefaultCommand() {
      // setDefaultCommand(new StoreShooterPiston());
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
      leftShooterPiston.setAngle(56);
      rightShooterPiston.setAngle(56);
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
