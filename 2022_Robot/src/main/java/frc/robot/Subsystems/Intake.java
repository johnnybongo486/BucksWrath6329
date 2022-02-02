package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    
    private final TalonSRX intakeTalon = new TalonSRX(5);

    public Intake() {
        intakeTalon.configOpenloopRamp(0.25);
        intakeTalon.configPeakOutputForward(1);
        intakeTalon.configPeakOutputReverse(-1);
        intakeTalon.setNeutralMode(NeutralMode.Brake);
    }

    public void reverseIntake() {
        intakeTalon.set(ControlMode.PercentOutput, .85);
    }

    public void runIntake() {
        intakeTalon.set(ControlMode.PercentOutput, -.85);
    }

    public void stopIntake(){
        intakeTalon.set(ControlMode.PercentOutput, 0);
    }
}