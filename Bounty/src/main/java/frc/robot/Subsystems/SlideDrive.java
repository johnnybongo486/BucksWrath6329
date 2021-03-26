package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Commands.Drivetrain.JoystickSlideDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class SlideDrive extends Subsystem {

    private boolean isSlideActive = false;

    public TalonFX slideESC = new TalonFX(4);
    public TalonFX slideFollowerESC = new TalonFX(5);

    public TalonFXConfiguration slideConfig = new TalonFXConfiguration();

    public double kP, kI, kD, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public int kIz;

    public void initDefaultCommand() {
        setDefaultCommand(new JoystickSlideDrive());
    }

    public SlideDrive() {
        // Setup Follower
        slideFollowerESC.follow(slideESC);

        // Setup Inverted
        slideESC.setInverted(false);
        slideFollowerESC.setInverted(true);

        setNeutralMode(NeutralMode.Brake);

        // PID coefficients
        kP = 0.1; 
        kI = 0;         // .000005
        kD = 0;         // .05
        kIz = 12;       // 12
        kF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        slideConfig.slot0.kF = kF;
        slideConfig.slot0.kP = kP;
        slideConfig.slot0.kI = kI;
        slideConfig.slot0.kD = kD;
        slideConfig.slot0.integralZone = kIz;
        slideConfig.slot0.closedLoopPeakOutput = kMaxOutput;
        
        maxRPM = 6380;

        // Set Timeouts
        int closedLoopTimeMs = 1;
		slideConfig.slot0.closedLoopPeriod = closedLoopTimeMs;
		slideConfig.slot1.closedLoopPeriod = closedLoopTimeMs;
		slideConfig.slot2.closedLoopPeriod = closedLoopTimeMs;
        slideConfig.slot3.closedLoopPeriod = closedLoopTimeMs;

        // Motion Magic Configs 
        slideConfig.motionAcceleration = 2000; //(distance units per 100 ms) per second
        slideConfig.motionCruiseVelocity = 10000; //distance units per 100 ms // could be up to 21k

        slideESC.configAllSettings(slideConfig);

        slideESC.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 30);
		slideESC.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);
		slideESC.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 30);
		slideESC.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, 30);

        slideESC.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        slideESC.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));

        slideESC.configOpenloopRamp(0.25);
        slideESC.configVoltageCompSaturation(8);
        slideESC.enableVoltageCompensation(true);
    }

    public void setNeutralMode(NeutralMode neutralMode) {
        this.slideESC.setNeutralMode(neutralMode);
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

    public void magicDrive (double distance) {
        this.slideESC.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
    }

    public double getSlideDistance() {
        return slideESC.getSelectedSensorPosition();
    }

    public void resetDriveEncoders() {
        slideESC.getSensorCollection().setIntegratedSensorPosition(0, 10);
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Slide Drive / Current", slideESC.getSupplyCurrent());
        SmartDashboard.putBoolean("Slide Drive / Active", isSlideActive());
    }

}