package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Commands.Shooter.DeployShooterPiston;

public class ShooterPiston extends Subsystem {

   private final Solenoid shooterSol = new Solenoid(0, 4); //I just put a random number here, to be changed

   public ShooterPiston(){
      
   }

   public void initDefaultCommand() {
      setDefaultCommand(new DeployShooterPiston());
   }

   public void store() {
      shooterSol.set(false);
   }

   public void deploy() {
      shooterSol.set(true);
      
   }
  
}
