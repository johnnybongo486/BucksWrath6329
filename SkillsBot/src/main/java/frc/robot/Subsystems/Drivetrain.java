package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Commands.Drivetrain.JoystickDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class Drivetrain extends Subsystem {

    public static int DRIVE_PROFILE = 0;
    public static int ROTATION_PROFILE = 1;

    public CustomTalonFX leftLead = new CustomTalonFX(2);
    public CustomTalonFX rightLead = new CustomTalonFX(3);
    public CustomTalonFX leftFollower = new CustomTalonFX(4);
    public CustomTalonFX rightFollower = new CustomTalonFX(5);
    public TalonSRX spareTalon = new TalonSRX(0);

    public PigeonIMU pigeon = new PigeonIMU(spareTalon);

    public double _leftOffset;
    public double _rightOffset;

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
        leftLead.setSensorPhase(true);
        rightLead.setSensorPhase(true);

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

        leftLead.configClosedLoopPeakOutput(0, 1.0);
        rightLead.configClosedLoopPeakOutput(0, 1.0);

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

    public void smartDrive (double leftRotations, double rightRotations) {
		this.leftLead.set(ControlMode.Position, leftRotations);
		this.rightLead.set(ControlMode.Position, rightRotations);
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
        this.leftLead.setSelectedSensorPosition(0);
        this.rightLead.setSelectedSensorPosition(0);

    }

    public double getDistance() {
        return rightLead.getSelectedSensorPosition();
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
}
