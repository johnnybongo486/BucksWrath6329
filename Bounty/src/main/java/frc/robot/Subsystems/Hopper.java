package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Commands.Serializer.stopHopper;
import frc.robot.Models.SRXGains;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hopper extends Subsystem{
    private TalonSRX HopperESC = new TalonSRX(10);

    public final static SRXGains kGains_Serializer = new SRXGains(0, 0.25, 0.001, 20, 1023.0/7200.0,  300);

    public void initDefaultCommand(){
        setDefaultCommand(new stopHopper());
   }
   
    public Hopper() {
        //Upper
        HopperESC.setInverted(false);
        HopperESC.configOpenloopRamp(0.25);

        /* Config the peak and nominal outputs */
		HopperESC.configNominalOutputForward(0, 30);
		HopperESC.configNominalOutputReverse(0, 30);
		HopperESC.configPeakOutputForward(1, 30);
        HopperESC.configPeakOutputReverse(-1, 30);
        
        HopperESC.config_kF(0, kGains_Serializer.F, 30);
		HopperESC.config_kP(1, kGains_Serializer.P, 30);
		HopperESC.config_kI(0, kGains_Serializer.I, 30);
        HopperESC.config_kD(0, kGains_Serializer.D, 30);
    }

    public void runHopper() {
        HopperESC.set(ControlMode.PercentOutput, 1.0) ;
    }

    public void reverseHopper() {
        HopperESC.set(ControlMode.PercentOutput, -0.8);
    }

    public void stopHopper() {
        HopperESC.set(ControlMode.PercentOutput, 0);

    }
}