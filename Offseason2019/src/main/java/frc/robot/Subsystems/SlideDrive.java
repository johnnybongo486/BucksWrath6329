package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Commands.JoystickSlideDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SlideDrive extends Subsystem {

    private boolean isSlideActive = false;

    public TalonSRX slideESC = new TalonSRX(0);

    public void initDefaultCommand() {
        setDefaultCommand(new JoystickSlideDrive());
    }

    public SlideDrive() {
        slideESC.setInverted(false);
        slideESC.configOpenloopRamp(0.25);
        slideESC.configContinuousCurrentLimit(60);
        slideESC.enableCurrentLimit(true);
    }

    public void drive(double slide) {
        this.slideESC.set(ControlMode.PercentOutput, slide);
    }

    public void stopSlide() {
        this.slideESC.set(ControlMode.PercentOutput,0);
    }

    public boolean isSlideActive() {
        return isSlideActive;
    }

    public void setSlideActive(boolean isSlideActive) {
        this.isSlideActive = isSlideActive;
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Slide Drive / Current", slideESC.getOutputCurrent());
        SmartDashboard.putBoolean("Slide Drive / Active", isSlideActive());
    }

}
