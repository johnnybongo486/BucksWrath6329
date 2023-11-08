package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PDP extends SubsystemBase {
    private final PowerDistribution PDP = new PowerDistribution(0, ModuleType.kCTRE);

    public PDP() {
        PDP.clearStickyFaults();
    }
}
