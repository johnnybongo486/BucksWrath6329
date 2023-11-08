package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ElevatorPiston extends SubsystemBase {
    
    private final Solenoid elevatorSol = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final Solenoid elevatorSol2 = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    public boolean isTipped = false;

    public ElevatorPiston(){
        
    }

    public void ElevatorIn() {
        elevatorSol.set(false);
        elevatorSol2.set(false);
    }

    public void ElevatorOut() {
        elevatorSol.set(true);
        elevatorSol2.set(true);

    }

    public void setIsTipped(boolean isTipped) {
        this.isTipped = isTipped;
    }

    public boolean getIsTipped() {
        return isTipped;
    }
}
