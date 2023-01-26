package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ElevatorPiston extends SubsystemBase {
    
    private final Solenoid elevatorSol = new Solenoid(PneumaticsModuleType.REVPH, 0);

    public ElevatorPiston(){
        
    }

    public void ElevatorIn() {
        elevatorSol.set(false);
    }

    public void ElevatorOut() {
        elevatorSol.set(true);
    }
}
