package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakePiston extends SubsystemBase {
    
    private final Solenoid intakeSol = new Solenoid(0, PneumaticsModuleType.CTREPCM, 1);

    public void intakeIn() {
        intakeSol.set(true);
    }

    public void intakeOut() {
        intakeSol.set(false);
    }
}
