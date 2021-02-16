package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Commands.Serializer.stopSerializer;
import frc.robot.Models.SRXGains;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Serializer extends Subsystem{
    private TalonSRX SerializerESC = new TalonSRX(12);

    public final static SRXGains kGains_Serializer = new SRXGains(0, 0.25, 0.001, 20, 1023.0/7200.0,  300);

    public void initDefaultCommand(){
        setDefaultCommand(new stopSerializer());
   }
   
    public Serializer() {
        SerializerESC.setInverted(true);
        SerializerESC.configOpenloopRamp(0.25);

        /* Config the peak and nominal outputs */
		SerializerESC.configNominalOutputForward(0, 30);
		SerializerESC.configNominalOutputReverse(0, 30);
		SerializerESC.configPeakOutputForward(1, 30);
        SerializerESC.configPeakOutputReverse(-1, 30);
        
        SerializerESC.config_kF(0, kGains_Serializer.F, 30);
		SerializerESC.config_kP(1, kGains_Serializer.P, 30);
		SerializerESC.config_kI(0, kGains_Serializer.I, 30);
        SerializerESC.config_kD(0, kGains_Serializer.D, 30);
    }

    public void runSerializer() {
        SerializerESC.set(ControlMode.PercentOutput, 1.0);
    }

    public void reverseSerializer() {
        SerializerESC.set(ControlMode.PercentOutput, -0.8);
    }

    public void stopSerializer() {
        SerializerESC.set(ControlMode.PercentOutput, 0);
    }


}