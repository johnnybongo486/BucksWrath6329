package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class Drivetrain extends SubsystemBase {

    private final TalonFX leftLead = new TalonFX(3);
    private final TalonFX leftFollow = new TalonFX(4);
    private final TalonFX rightLead = new TalonFX(1);
    private final TalonFX rightFollow = new TalonFX(2);
  
    private final TalonSRX spareTalon = new TalonSRX(5);
    private PigeonIMU pigeon = new PigeonIMU(spareTalon);

    // Set Motor Directions
    TalonFXInvertType rightInvert = TalonFXInvertType.CounterClockwise;
    TalonFXInvertType leftInvert = TalonFXInvertType.Clockwise;

    // For Tracking High/Low Gear
    private boolean isHighGear = false;

    public Drivetrain() {
        leftLead.setInverted(leftInvert);
        rightLead.setInverted(rightInvert);
        leftFollow.setInverted(leftInvert);
        rightFollow.setInverted(rightInvert);

        leftFollow.follow(leftLead);
        rightFollow.follow(rightLead);
        
        // Zero Sensors
        resetPigeon();
        resetDriveEncoders();
        setNeutralMode(NeutralMode.Brake);

        // Set Ramp Rates
        leftLead.configOpenloopRamp(0.25);
        rightLead.configOpenloopRamp(0.25);
        leftFollow.configOpenloopRamp(0.25);
        rightFollow.configOpenloopRamp(0.25);

        leftLead.configClosedloopRamp(0.25);
        rightLead.configClosedloopRamp(0.25);
        leftFollow.configClosedloopRamp(0.25);
        rightFollow.configClosedloopRamp(0.25);

        // Set Peak Output
        leftLead.configClosedLoopPeakOutput(0, 1.0);
        rightLead.configClosedLoopPeakOutput(0, 1.0);
        leftFollow.configClosedLoopPeakOutput(0, 1.0);
        rightFollow.configClosedLoopPeakOutput(0, 1.0);

        /**
         * Configure the current limits that will be used Stator Current is the current
         * that passes through the motor stators. Use stator current limits to limit
         * rotor acceleration/heat production Supply Current is the current that passes
         * into the controller from the supply Use supply current limits to prevent
         * breakers from tripping
         * enabled | Limit(amp) | Trigger Threshold(amp) | Trigger Threshold Time(s) */
        leftLead.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        leftLead.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));
        leftFollow.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        leftFollow.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));

        rightLead.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        rightLead.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));
        rightFollow.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 100, 1.0));
        rightFollow.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 100, 0.5));
    }

    @Override
    public void periodic() {
    }

    public void teleopDrive() {
        double moveValue = 0;
        double rotateValue = 0;
        double left = 0;
        double right = 0;

        moveValue = Robot.robotContainer.getDriverLeftStickY();
        rotateValue = -Robot.robotContainer.getDriverRightStickX();

        left = moveValue + rotateValue;
        right = moveValue - rotateValue;

        leftLead.set(ControlMode.PercentOutput, left);
        rightLead.set(ControlMode.PercentOutput, right);
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

    public void setNeutralMode(NeutralMode neutralMode) {
        this.leftLead.setNeutralMode(neutralMode);
        this.rightLead.setNeutralMode(neutralMode);
    }

    public void resetDriveEncoders() {
        leftLead.getSensorCollection().setIntegratedSensorPosition(0, 10);
        rightLead.getSensorCollection().setIntegratedSensorPosition(0, 10);
    }

    public double getRightDistance() {
        return rightLead.getSelectedSensorPosition();
    }

    public double getLeftDistance() {
        return leftLead.getSelectedSensorPosition();
    }

    public double getLeftSpeed() {
        return leftLead.getSelectedSensorVelocity();
    }

    public double getRightSpeed() {
        return rightLead.getSelectedSensorVelocity();
    }

    public boolean isHighGear() {
        return isHighGear;
    }

    public void setIsHighGear(boolean isHighGear) {
        this.isHighGear = isHighGear;
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Drivetrain / Left Lead Current", leftLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Lead Current", rightLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Speed", getLeftSpeed());
        SmartDashboard.putNumber("Drivetrain / Right Speed", getRightSpeed());
        SmartDashboard.putNumber("Drivetrain / Current Angle", getAngle());
        SmartDashboard.putNumber("Drivetrain / Current Angular Rate", getRoll());
        SmartDashboard.putNumber("Drivetrain / Left Distance", leftLead.getSelectedSensorPosition());
        SmartDashboard.putNumber("Drivetrain / Right Distance", rightLead.getSelectedSensorPosition());
        SmartDashboard.putBoolean("Drivetrain / High Gear", isHighGear());
    }
}
