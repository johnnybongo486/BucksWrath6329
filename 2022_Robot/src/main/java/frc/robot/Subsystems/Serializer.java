package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Serializer extends SubsystemBase {
    
    private final TalonSRX SerializerTalon = new TalonSRX(8);
    private final TalonSRX UpperSerializerTalon = new TalonSRX(7);

    public Serializer() {
        SerializerTalon.configFactoryDefault();
        UpperSerializerTalon.configFactoryDefault();
        SerializerTalon.clearStickyFaults();
        UpperSerializerTalon.clearStickyFaults();

        SerializerTalon.configOpenloopRamp(0.25);
        SerializerTalon.configPeakOutputForward(1);
        SerializerTalon.configPeakOutputReverse(-1);
        SerializerTalon.setNeutralMode(NeutralMode.Brake);
        SerializerTalon.setStatusFramePeriod(StatusFrame.Status_1_General, 250, 10);

        UpperSerializerTalon.configOpenloopRamp(0.25);
        UpperSerializerTalon.configPeakOutputForward(1);
        UpperSerializerTalon.configPeakOutputReverse(-1);
        UpperSerializerTalon.setNeutralMode(NeutralMode.Coast);
        UpperSerializerTalon.setStatusFramePeriod(StatusFrame.Status_1_General, 250, 10);
    }

    public void reverseSerializer() {
        SerializerTalon.set(ControlMode.PercentOutput, -1);
        UpperSerializerTalon.set(ControlMode.PercentOutput, 1);
    }

    public void runSerializer() {
        SerializerTalon.set(ControlMode.PercentOutput, 1);
        UpperSerializerTalon.set(ControlMode.PercentOutput, -1);  // was 0.7
    }

    public void stopSerializer(){
        SerializerTalon.set(ControlMode.PercentOutput, 0);
        UpperSerializerTalon.set(ControlMode.PercentOutput, 0);
    }
}