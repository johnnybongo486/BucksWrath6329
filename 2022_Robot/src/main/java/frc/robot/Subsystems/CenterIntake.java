package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CenterIntake extends SubsystemBase {
    
    private final TalonSRX centerTalon = new TalonSRX(6);

    public CenterIntake() {
        centerTalon.configFactoryDefault();
        centerTalon.clearStickyFaults();
        centerTalon.configOpenloopRamp(0.25);
        centerTalon.configPeakOutputForward(1);
        centerTalon.configPeakOutputReverse(-1);
        centerTalon.setNeutralMode(NeutralMode.Brake);
        centerTalon.setStatusFramePeriod(StatusFrame.Status_1_General, 250, 10);
    }

    public void reverseCenterIntake() {
        centerTalon.set(ControlMode.PercentOutput, -1);
    }

    public void runCenterIntake() {
        centerTalon.set(ControlMode.PercentOutput, 1);
    }

    public void stopCenterIntake(){
        centerTalon.set(ControlMode.PercentOutput, 0);
    }
}
