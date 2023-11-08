package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    
    private final TalonSRX lowerIntakeTalon = new TalonSRX(13);
    private final TalonSRX upperIntakeTalon = new TalonSRX(14);

    public Intake() {
        lowerIntakeTalon.configFactoryDefault();
        lowerIntakeTalon.clearStickyFaults();
        lowerIntakeTalon.configOpenloopRamp(0.25);
        lowerIntakeTalon.configPeakOutputForward(1);
        lowerIntakeTalon.configPeakOutputReverse(-1);
        lowerIntakeTalon.configPeakCurrentLimit(20);
        lowerIntakeTalon.configPeakCurrentDuration(10);
        lowerIntakeTalon.configContinuousCurrentLimit(15);
        lowerIntakeTalon.enableCurrentLimit(true);
        lowerIntakeTalon.setNeutralMode(NeutralMode.Brake);
        lowerIntakeTalon.setStatusFramePeriod(StatusFrame.Status_1_General, 251, 10);

        upperIntakeTalon.configFactoryDefault();
        upperIntakeTalon.clearStickyFaults();
        upperIntakeTalon.configOpenloopRamp(0.25);
        upperIntakeTalon.configPeakOutputForward(1);
        upperIntakeTalon.configPeakOutputReverse(-1);
        upperIntakeTalon.configPeakCurrentLimit(20);
        upperIntakeTalon.configPeakCurrentDuration(10);
        upperIntakeTalon.configContinuousCurrentLimit(15);
        upperIntakeTalon.enableCurrentLimit(true);
        upperIntakeTalon.setNeutralMode(NeutralMode.Brake);
        upperIntakeTalon.setStatusFramePeriod(StatusFrame.Status_1_General, 253, 10);
    }

    // Cone Commands
    public void intakeCone() {
        lowerIntakeTalon.set(ControlMode.PercentOutput, -1);
        upperIntakeTalon.set(ControlMode.PercentOutput, -1);
    }

    public void holdCone(){
        lowerIntakeTalon.set(ControlMode.PercentOutput, -0.2);
        upperIntakeTalon.set(ControlMode.PercentOutput, -0.2);
    }

    public void scoreCone() {
        lowerIntakeTalon.set(ControlMode.PercentOutput, 0.7);
        upperIntakeTalon.set(ControlMode.PercentOutput, 0.7);
    }

    // Cube Commands
    public void intakeCube() {
        lowerIntakeTalon.set(ControlMode.PercentOutput, -0.9);
        upperIntakeTalon.set(ControlMode.PercentOutput, 0.9);
    }

    public void holdCube() {
        lowerIntakeTalon.set(ControlMode.PercentOutput, 0.0);
        upperIntakeTalon.set(ControlMode.PercentOutput, 0.2);
    }

    public void scoreCube(){
        lowerIntakeTalon.set(ControlMode.PercentOutput, 0.5);
        upperIntakeTalon.set(ControlMode.PercentOutput, -0.5);
    }

    public void spitCube(){
        lowerIntakeTalon.set(ControlMode.PercentOutput, 1);
        upperIntakeTalon.set(ControlMode.PercentOutput, -1);
    }

    // Stop Intake
    public void stopIntake(){
        lowerIntakeTalon.set(ControlMode.PercentOutput, 0);
        upperIntakeTalon.set(ControlMode.PercentOutput, 0);
    }

    // Get Power Draw
    public double getUpperIntakeAmps(){
        return upperIntakeTalon.getSupplyCurrent();
    }

    public double getLowerIntakeAmps(){
        return lowerIntakeTalon.getSupplyCurrent();
    }
}
