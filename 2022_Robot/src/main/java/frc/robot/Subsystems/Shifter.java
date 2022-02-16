package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;


public class Shifter extends SubsystemBase {
    
    private final Solenoid shifterSol = new Solenoid(1, PneumaticsModuleType.REVPH, 9);

    public Shifter() {
        
    }

    public void lowGear() {
        shifterSol.set(false);
        RobotContainer.drivetrain.setIsHighGear(true);
    }

    public void highGear() {
        shifterSol.set(true);
        RobotContainer.drivetrain.setIsHighGear(false);
    }
}
