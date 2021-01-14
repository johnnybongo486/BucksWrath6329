package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Commands.Serializer.stopSerializer;
import frc.robot.Models.SRXGains;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Serializer extends Subsystem{
    private VictorSPX LowerSerializerESC = new VictorSPX(3);
    private VictorSPX UpperSerializerESC = new VictorSPX(4);

    private DigitalInput input = new DigitalInput(0);
    public final static SRXGains kGains_Serializer = new SRXGains(0, 0.25, 0.001, 20, 1023.0/7200.0,  300);


    public void initDefaultCommand(){
        setDefaultCommand(new stopSerializer());
   }
   
    public Serializer() {
        LowerSerializerESC.setInverted(true);
        LowerSerializerESC.configOpenloopRamp(0.25);

        /* Config the peak and nominal outputs */
		LowerSerializerESC.configNominalOutputForward(0, 30);
		LowerSerializerESC.configNominalOutputReverse(0, 30);
		LowerSerializerESC.configPeakOutputForward(1, 30);
        LowerSerializerESC.configPeakOutputReverse(-1, 30);
        
        LowerSerializerESC.config_kF(0, kGains_Serializer.F, 30);
		LowerSerializerESC.config_kP(1, kGains_Serializer.P, 30);
		LowerSerializerESC.config_kI(0, kGains_Serializer.I, 30);
        LowerSerializerESC.config_kD(0, kGains_Serializer.D, 30);

        //Upper
        UpperSerializerESC.setInverted(true);
        UpperSerializerESC.configOpenloopRamp(0.25);

        /* Config the peak and nominal outputs */
		UpperSerializerESC.configNominalOutputForward(0, 30);
		UpperSerializerESC.configNominalOutputReverse(0, 30);
		UpperSerializerESC.configPeakOutputForward(1, 30);
        UpperSerializerESC.configPeakOutputReverse(-1, 30);
        
        UpperSerializerESC.config_kF(0, kGains_Serializer.F, 30);
		UpperSerializerESC.config_kP(1, kGains_Serializer.P, 30);
		UpperSerializerESC.config_kI(0, kGains_Serializer.I, 30);
        UpperSerializerESC.config_kD(0, kGains_Serializer.D, 30);
    }
    
    public boolean readInput() {
        return input.get();
    }

    public void slowSerializer() {
        LowerSerializerESC.set(ControlMode.PercentOutput, 0.4);
        UpperSerializerESC.set(ControlMode.PercentOutput, 0.4);

    }

    public void runSerializer() {
        LowerSerializerESC.set(ControlMode.PercentOutput, .9);
        UpperSerializerESC.set(ControlMode.PercentOutput, .9) ;

    }

    public void reverseSerializer() {
        LowerSerializerESC.set(ControlMode.PercentOutput, -0.3);
        UpperSerializerESC.set(ControlMode.PercentOutput, -0.3);
    }

    public void SerializerStop() {
        LowerSerializerESC.set(ControlMode.PercentOutput, 0);
        UpperSerializerESC.set(ControlMode.PercentOutput, 0);
    }

    public void updateDashboard() {
        SmartDashboard.putBoolean("IR Break Beam", this.readInput());
    }
}