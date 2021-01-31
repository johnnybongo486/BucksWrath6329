package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Commands.Drivetrain.JoystickSlideDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class SlideDrive extends Subsystem {

    private boolean isSlideActive = false;

    public TalonFX slideESC = new TalonFX(0);
    public TalonFX slideFollowerESC = new TalonFX(1);


    public void initDefaultCommand() {
        setDefaultCommand(new JoystickSlideDrive());
    }

    public SlideDrive() {
        // Setup Follower
        slideFollowerESC.follow(slideESC);

        // Setup Inverted
        slideESC.setInverted(false);
        slideFollowerESC.setInverted(false);

        slideESC.configOpenloopRamp(0.25);
        // slideESC.configContinuousCurrentLimit(60);
        // slideESC.enableCurrentLimit(true);

        slideESC.configOpenloopRamp(0.25);
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
        SmartDashboard.putNumber("Slide Drive / Current", slideESC.getSupplyCurrent());
        SmartDashboard.putBoolean("Slide Drive / Active", isSlideActive());
    }

}