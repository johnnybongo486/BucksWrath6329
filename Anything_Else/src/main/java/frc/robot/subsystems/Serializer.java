package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Serializer extends SubsystemBase {
    
    private final TalonSRX serializerTalon = new TalonSRX(13);

    public Serializer() {
        serializerTalon.configFactoryDefault();
        serializerTalon.clearStickyFaults();
        serializerTalon.configOpenloopRamp(0.25);
        serializerTalon.configPeakOutputForward(1);
        serializerTalon.configPeakOutputReverse(-1);
        serializerTalon.configPeakCurrentLimit(20);
        serializerTalon.configPeakCurrentDuration(10);
        serializerTalon.configContinuousCurrentLimit(15);
        serializerTalon.enableCurrentLimit(true);
        serializerTalon.setNeutralMode(NeutralMode.Brake);
        serializerTalon.setStatusFramePeriod(StatusFrame.Status_1_General, 251, 10);
    }

    // Serializer Commands

    public void runSerializer(){
        serializerTalon.set(ControlMode.PercentOutput, 0.5);
    }

    public void spitCube(){
        serializerTalon.set(ControlMode.PercentOutput, 1);
    }

    // Stop Serializer
    public void stopSerializer(){
        serializerTalon.set(ControlMode.PercentOutput, 0);
    }

    public double getSerializerAmps(){
        return serializerTalon.getSupplyCurrent();
    }
}
