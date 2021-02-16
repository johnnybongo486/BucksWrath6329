package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Commands.Intake.IntakeStop;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem{
    public TalonSRX IntakeESC = new TalonSRX(11);

    public Intake() {
        IntakeESC.setInverted(true);
        IntakeESC.configOpenloopRamp(0.25);

        /* Config the peak and nominal outputs */
		IntakeESC.configNominalOutputForward(0, 30);
		IntakeESC.configNominalOutputReverse(0, 30);
		IntakeESC.configPeakOutputForward(1, 30);
        IntakeESC.configPeakOutputReverse(-1, 30);

        IntakeESC.configContinuousCurrentLimit(20, 30);
        IntakeESC.configPeakCurrentLimit(30, 30);
    }

    public void initDefaultCommand(){
        setDefaultCommand(new IntakeStop());
    }

    public void runIntake() {
        IntakeESC.set(ControlMode.PercentOutput, 1);
    }

    public void reverseIntake() {
        IntakeESC.set(ControlMode.PercentOutput, -1);
    }

    public void stopIntake() {
        IntakeESC.set(ControlMode.PercentOutput, 0);
    }
}