package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.models.CustomTalonFX;

public class Intake extends SubsystemBase {
    
    private final CustomTalonFX intakeFalcon = new CustomTalonFX(13);
    public StatorCurrentLimitConfiguration statorCurrent = new StatorCurrentLimitConfiguration(false, 10, 40, 500);
    public SupplyCurrentLimitConfiguration supplyCurrent = new SupplyCurrentLimitConfiguration(false, 10, 40, 500);


    public Intake() {
        intakeFalcon.configFactoryDefault();
        intakeFalcon.clearStickyFaults();
        intakeFalcon.configOpenloopRamp(0.25);
        intakeFalcon.configPeakOutputForward(1);
        intakeFalcon.configStatorCurrentLimit(statorCurrent);
        intakeFalcon.configSupplyCurrentLimit(supplyCurrent);
        intakeFalcon.configPeakOutputReverse(-1);
        intakeFalcon.setNeutralMode(NeutralMode.Brake);
    }

    // Cube Commands
    public void intakeCube() {
        intakeFalcon.set(ControlMode.PercentOutput, 0.5);
    }

    public void holdCube() {
        intakeFalcon.set(ControlMode.PercentOutput, 0.0);
    }

    public void spitCube(){
        intakeFalcon.set(ControlMode.PercentOutput, -0.3);
    }


    // Stop Intake
    public void stopIntake(){
        intakeFalcon.set(ControlMode.PercentOutput, 0);
    }

    // Get Power Draw
    public double getIntakeAmps(){
        return intakeFalcon.getSupplyCurrent();
    }
}

