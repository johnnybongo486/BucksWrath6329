package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Serializer extends SubsystemBase {
    
    private final TalonSRX SerializerTalon = new TalonSRX(8);

    public Serializer() {
        SerializerTalon.configOpenloopRamp(0.25);
        SerializerTalon.configPeakOutputForward(1);
        SerializerTalon.configPeakOutputReverse(-1);
        SerializerTalon.setNeutralMode(NeutralMode.Brake);
    }

    public void reverseSerializer() {
        SerializerTalon.set(ControlMode.PercentOutput, -1);
    }

    public void runSerializer() {
        SerializerTalon.set(ControlMode.PercentOutput, 1);
    }

    public void stopSerializer(){
        SerializerTalon.set(ControlMode.PercentOutput, 0);
    }
}