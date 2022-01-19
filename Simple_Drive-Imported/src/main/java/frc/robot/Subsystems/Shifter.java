package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;


public class Shifter extends SubsystemBase {
    
    private final Solenoid shifterSol = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);

    public void lowGear() {
        shifterSol.set(true);
        RobotContainer.drivetrain.setIsHighGear(false);
    }

    public void highGear() {
        shifterSol.set(false);
        RobotContainer.drivetrain.setIsHighGear(true);
    }
}
