package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Commands.Intake.StopIntake;


public class Intake extends Subsystem {

    public VictorSPX leftIntakeESC = new VictorSPX(0);
    public VictorSPX rightIntakeESC = new VictorSPX(1);

    public void initDefaultCommand() {
        setDefaultCommand(new StopIntake());
    }

    public Intake() {
        leftIntakeESC.setInverted(true);
        leftIntakeESC.configOpenloopRamp(0.1);

        rightIntakeESC.setInverted(false);
        rightIntakeESC.configOpenloopRamp(0.1);

        leftIntakeESC.setNeutralMode(NeutralMode.Brake);
        rightIntakeESC.setNeutralMode(NeutralMode.Brake);
    }

    public void intakeCargo() {
        this.leftIntakeESC.set(ControlMode.PercentOutput, 0.3);
        this.rightIntakeESC.set(ControlMode.PercentOutput, 0.3);
    }

    public void scoreCargo() {
        this.leftIntakeESC.set(ControlMode.PercentOutput, -0.5);
        this.rightIntakeESC.set(ControlMode.PercentOutput, -0.5);
    }

    public void intakeHatch() {
        this.leftIntakeESC.set(ControlMode.PercentOutput, -0.7);
        this.rightIntakeESC.set(ControlMode.PercentOutput, -0.7);
    }

    public void scoreHatch() {
        this.leftIntakeESC.set(ControlMode.PercentOutput, 1.0);
        this.rightIntakeESC.set(ControlMode.PercentOutput, 1.0);
    }

    public void stopIntake() {
        this.leftIntakeESC.set(ControlMode.PercentOutput, -0.05);
        this.rightIntakeESC.set(ControlMode.PercentOutput,-0.05);
    }

    public void holdHAtch() {
        this.leftIntakeESC.set(ControlMode.PercentOutput, -0.05);
        this.rightIntakeESC.set(ControlMode.PercentOutput, -0.05);
    }
    
    public void holdCargo() {
        this.leftIntakeESC.set(ControlMode.PercentOutput, 0.05);
        this.rightIntakeESC.set(ControlMode.PercentOutput, 0.05);
    }

    public void updateDashboard() {

    }

}
