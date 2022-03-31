package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberPiston extends SubsystemBase {
    
    private final Solenoid climberSol = new Solenoid(0, PneumaticsModuleType.CTREPCM, 1);

    public ClimberPiston() {
        
    }

    public void climberIn() {
        climberSol.set(false);
    }

    public void climberOut() {
        climberSol.set(true);
    }
}
