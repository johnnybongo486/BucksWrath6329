package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Commands.Intake.StoreIntakePiston;

public class IntakePiston extends Subsystem {

   private final Solenoid intakeSol = new Solenoid(0, 7);

   public IntakePiston(){
      
   }

   public void initDefaultCommand() {
      setDefaultCommand(new StoreIntakePiston());
   }

   public void store() {
      intakeSol.set(false);
   }

   public void deploy() {
      intakeSol.set(true);
   }
  
}
