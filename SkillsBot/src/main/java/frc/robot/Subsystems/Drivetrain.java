package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Commands.Drivetrain.JoystickDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU;

public class Drivetrain extends Subsystem {

    public static int DRIVE_PROFILE = 0;
    public static int ROTATION_PROFILE = 1;

    public double kP = 0.175; 
    public double kI = 0;         // .000005
    public double kD = 0;         // .05
    public int kIz = 12;       // 12
    public double kF = 0; 
    public double kMaxOutput = 1; 
    public double kMinOutput = -1;
            
    public double tkP = 0.175; 
    public double tkI = 0;
    public double tkD = 0; 
    public int tkIz = 12; 
    public double tkF = 0; 
    public double tkMaxOutput = 1; 
    public double tkMinOutput = -1;

    public CustomTalonFX leftLead = new CustomTalonFX(0);
    public CustomTalonFX rightLead = new CustomTalonFX(2);
    public CustomTalonFX leftFollower = new CustomTalonFX(1);
    public CustomTalonFX rightFollower = new CustomTalonFX(3);
    public CustomTalonSRX spareTalon = new CustomTalonSRX(9);

    public PigeonIMU pigeon = new PigeonIMU(spareTalon);

    public TalonFXConfiguration leftConfig = new TalonFXConfiguration();
    public TalonFXConfiguration rightConfig = new TalonFXConfiguration();

    TalonFXInvertType rightInvert = TalonFXInvertType.CounterClockwise;
    TalonFXInvertType leftInvert = TalonFXInvertType.Clockwise;

    public void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    public Drivetrain() {
        // Setup Followers
        leftFollower.follow(leftLead);
        rightFollower.follow(rightLead);

        // Set Motor Direction
        leftLead.setInverted(true);
        leftFollower.setInverted(true);
        rightLead.setInverted(false);
        rightFollower.setInverted(false);

        // Set Sensors
        leftLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        rightLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        
        // ESC Settings
        resetPigeon();
        setNeutralMode(NeutralMode.Brake);

        /**
         * Configure the current limits that will be used Stator Current is the current
         * that passes through the motor stators. Use stator current limits to limit
         * rotor acceleration/heat production Supply Current is the current that passes
         * into the controller from the supply Use supply current limits to prevent
         * breakers from tripping

         * enabled | Limit(amp) | Trigger Threshold(amp) | Trigger Threshold Time(s) */
        leftLead.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        leftLead.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));
        leftFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        leftFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));

        rightLead.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        rightLead.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));
        rightFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        rightFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));
       
        // Set Ramp Rates
        leftLead.configOpenloopRamp(0.25);
        rightLead.configOpenloopRamp(0.25);

        leftLead.configClosedloopRamp(0.25);
        rightLead.configClosedloopRamp(0.25);

        // leftLead.configClosedLoopPeakOutput(0, 1.0);
        // rightLead.configClosedLoopPeakOutput(0, 1.0);

        // FPID for Distance 
        rightConfig.slot0.kF = kF;
        rightConfig.slot0.kP = kP;
        rightConfig.slot0.kI = kI;
        rightConfig.slot0.kD = kD;
        rightConfig.slot0.integralZone = kIz;
        rightConfig.slot0.closedLoopPeakOutput = kMaxOutput;
        leftConfig.slot0.kF = kF;
        leftConfig.slot0.kP = kP;
        leftConfig.slot0.kI = kI;
        leftConfig.slot0.kD = kD;
        leftConfig.slot0.integralZone = kIz;
        leftConfig.slot0.closedLoopPeakOutput = kMaxOutput;

        //FPID for Heading
        rightConfig.slot1.kF = tkF;
        rightConfig.slot1.kP = tkP;
        rightConfig.slot1.kI = tkI;
        rightConfig.slot1.kD = tkD;
        rightConfig.slot1.integralZone = tkIz;
        rightConfig.slot1.closedLoopPeakOutput = tkMaxOutput;
        leftConfig.slot1.kF = tkF;
        leftConfig.slot1.kP = tkP;
        leftConfig.slot1.kI = tkI;
        leftConfig.slot1.kD = tkD;
        leftConfig.slot1.integralZone = tkIz;
        leftConfig.slot1.closedLoopPeakOutput = tkMaxOutput;

        int closedLoopTimeMs = 1;
		rightConfig.slot0.closedLoopPeriod = closedLoopTimeMs;
		rightConfig.slot1.closedLoopPeriod = closedLoopTimeMs;
		rightConfig.slot2.closedLoopPeriod = closedLoopTimeMs;
        rightConfig.slot3.closedLoopPeriod = closedLoopTimeMs;
        leftConfig.slot0.closedLoopPeriod = closedLoopTimeMs;
		leftConfig.slot1.closedLoopPeriod = closedLoopTimeMs;
		leftConfig.slot2.closedLoopPeriod = closedLoopTimeMs;
        leftConfig.slot3.closedLoopPeriod = closedLoopTimeMs;

        rightConfig.motionAcceleration = 2000; //(distance units per 100 ms) per second
        rightConfig.motionCruiseVelocity = 5000; //distance units per 100 ms // could be up to 21k
        leftConfig.motionAcceleration = 2000; //(distance units per 100 ms) per second
        leftConfig.motionCruiseVelocity = 5000; //distance units per 100 ms // could be up to 21k

        leftLead.configAllSettings(leftConfig);
        rightLead.configAllSettings(rightConfig);

        // rightLead.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 30);
		// rightLead.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);
		// rightLead.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 30);
		// rightLead.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, 30);
		// leftLead.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 30);
        // pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR , 5, 30);
        
        rightLead.selectProfileSlot(0, 0);
        rightLead.selectProfileSlot(1, 1);
        leftLead.selectProfileSlot(0, 0);
        leftLead.selectProfileSlot(1, 1);
    }

    public void drive(ControlMode controlMode, double left, double right) {
        this.leftLead.set(controlMode, left);
        this.rightLead.set(controlMode, right);
    }

    public void stopDrivetrain() {
        this.leftLead.set(ControlMode.PercentOutput, 0);
        this.rightLead.set(ControlMode.PercentOutput, 0);
    }

    public void drive(ControlMode controlMode, DriveSignal driveSignal) {
        this.drive(controlMode, driveSignal.getLeft(), driveSignal.getRight());
    }

    public void setNeutralMode(NeutralMode neutralMode) {
        this.leftLead.setNeutralMode(neutralMode);
        this.rightLead.setNeutralMode(neutralMode);
    }

    public double getAngle() {
        double[] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);
        return ypr[0];
    }

    public double getRoll() {
        double[] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);
        return ypr[2];
    }

    public void resetPigeon() {
        this.pigeon.setYaw(0.0, 0);
    }

    public void resetDriveEncoders() {
        leftLead.getSensorCollection().setIntegratedSensorPosition(0, 10);
        rightLead.getSensorCollection().setIntegratedSensorPosition(0, 10);

    }

    public double getLeftSpeed() {
        return leftLead.getSelectedSensorVelocity();
    }

    public double getRightSpeed() {
        return rightLead.getSelectedSensorVelocity();
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Drivetrain / Left Lead Current", leftLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Lead Current", rightLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Speed", getLeftSpeed());
        SmartDashboard.putNumber("Drivetrain / Right Speed", getRightSpeed());
        SmartDashboard.putNumber("Drivetrain / Current Angle", getAngle());
        SmartDashboard.putNumber("Drivetrain / Current Angular Rate", getRoll());
    }

    public double getDistance() {
        return rightLead.getSelectedSensorPosition();
      }
}
