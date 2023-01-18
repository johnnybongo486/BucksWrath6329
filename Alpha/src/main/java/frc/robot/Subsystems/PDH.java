package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PDH extends SubsystemBase {
    private final PowerDistribution PDH = new PowerDistribution(9, ModuleType.kRev);

    public PDH() {
        PDH.clearStickyFaults();
    }
}
