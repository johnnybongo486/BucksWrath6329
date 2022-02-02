package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberPiston extends SubsystemBase {
    
    private final Solenoid climberSol = new Solenoid(1, PneumaticsModuleType.REVPH, 9);

    public void climberIn() {
        climberSol.set(true);
    }

    public void climberOut() {
        climberSol.set(false);
    }
}
