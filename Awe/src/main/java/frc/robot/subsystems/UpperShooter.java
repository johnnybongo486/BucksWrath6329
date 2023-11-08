package frc.robot.subsystems;

import frc.robot.Robot;
import frc.lib.models.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class UpperShooter extends SubsystemBase implements IVelocityControlledSubsystem {

	private boolean isHoldingVelocity = false;

	// Set Different Speeds
	private double conversionFactor = 4096 / 600;
	private double zeroVelocity = 0*conversionFactor;
	private double closeShotVelocity = 500*conversionFactor;
	private double maxVelocity = 5000*conversionFactor;

	public final static int Shooter_PIDX = 0;

	public double maxVelocityLimit = maxVelocity;
	public double lowVelocityLimit = 0;
	private double targetVelocity = 0;
	private double arbitraryFeedForward = 0.0;

	private final static double onTargetThreshold = 100;
	
	private final SRXGains shooterGains = new SRXGains(Shooter_PIDX, 0.05, 0, 0, .05, 100);//0.2, 0, 0, 0.05, 100
	private final SRXGains slowShooterGains = new SRXGains(Shooter_PIDX, 0.05, 0, 0, 0, 100);//0.2, 0, 0, 0.05, 100

	//Uses PID values to go to a Velocity
	private MotionParameters shooterMotionParameters = new MotionParameters(10000, 20000, shooterGains);// velocity could be up to 21k
	private MotionParameters slowShooterMotionParameters = new MotionParameters(10000, 20000, slowShooterGains);// 4700

	
	private double peakOutputForward = 1.0;
	private double peakOutputReverse = -1.0;

	public CustomTalonFX UpperShooterFalcon = new CustomTalonFX(14);

	public UpperShooter() {
		this.UpperShooterFalcon.configFactoryDefault();
		this.UpperShooterFalcon.clearStickyFaults();

		this.UpperShooterFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

		this.UpperShooterFalcon.configForwardSoftLimitEnable(false);
		this.UpperShooterFalcon.configReverseSoftLimitEnable(false);

		this.UpperShooterFalcon.setInverted(true);
		this.UpperShooterFalcon.setSensorPhase(false);

		this.UpperShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		this.UpperShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
		
		this.UpperShooterFalcon.setNeutralMode(NeutralMode.Coast);
		this.UpperShooterFalcon.configClosedloopRamp(1);

		this.UpperShooterFalcon.configVoltageCompSaturation(11.5);
		this.UpperShooterFalcon.enableVoltageCompensation(true);

		this.UpperShooterFalcon.configPeakOutputReverse(peakOutputReverse, 30);
		this.UpperShooterFalcon.configPeakOutputForward(peakOutputForward, 30);

		this.UpperShooterFalcon.configMotionParameters(shooterMotionParameters);

		this.resetShooterEncoder();
	}

	//sets control mode to motion magic
	public void setShooter(ControlMode controlMode, double signal) {
		if (controlMode == ControlMode.Velocity) {
			this.manageVelocity(signal);
		}
		UpperShooterFalcon.set(controlMode, signal);
	}

	public void setShooter(ControlMode controlMode, double signal, DemandType demandType, double demand) {
		if (controlMode == ControlMode.Velocity) {
			this.manageVelocity(signal);
		}
		UpperShooterFalcon.set(controlMode, signal, demandType, demand);
	}

	public void velocityControl() {
		this.manageVelocity(targetVelocity);
		this.UpperShooterFalcon.set(ControlMode.Velocity, targetVelocity, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
	}

	public double getCurrentDraw() {
		return this.UpperShooterFalcon.getSupplyCurrent();
	}

	public boolean isHoldingVelocity() {
		return this.isHoldingVelocity;
	}

	public void setIsHoldingVelocity(boolean isHoldingVelocity) {
		this.isHoldingVelocity = isHoldingVelocity;
	}

	public double getTargetVelocity() {
		return this.targetVelocity;
	}

	public boolean setTargetVelocity(double Velocity) {
		if (!isValidVelocity(Velocity)) {
			return false;
		} else {
			this.targetVelocity = Velocity;
			return true;
		}
	}

	public void forceSetTargetVelocity(double Velocity) {
		this.targetVelocity = Velocity;
	}

	public void incrementTargetVelocity(double increment) {
		double currentTargetVelocity = this.targetVelocity;
		double newTargetVelocity = currentTargetVelocity + increment;
		if (isValidVelocity(newTargetVelocity)) {
			this.targetVelocity = newTargetVelocity;
		}
	}

	public boolean isValidVelocity(double Velocity) {
		boolean withinBounds = Velocity <= maxVelocityLimit && Velocity >= lowVelocityLimit;
		return withinBounds;
	}

    // communicate with commands
	public double getZeroVelocity() {
		return this.zeroVelocity;
	}

	public double getMaxVelocity() {
		return this.maxVelocity;
	}

	public double getCloseShotVelocity() {
		return this.closeShotVelocity;
	}

	public double getArbitraryFeedForward() {
		return this.arbitraryFeedForward;
	}

	public void resetShooterEncoder() {
        try {
			UpperShooterFalcon.setSelectedSensorPosition(0, 0, 30);
        }
        catch (Exception e) {
            DriverStation.reportError("Shooter.resetShooterEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public double getVelocityError() {
		double currentVelocity = this.getCurrentVelocity();
		double targetVelocity = this.getTargetVelocity();
		double VelocityError = Math.abs(currentVelocity - targetVelocity);
		return VelocityError;
	}

	public double joystickShooter(){
		double value = 0;
		value = -Robot.m_robotContainer.getOperatorRightStickY();
		return value;
	}

	public void manageVelocity(double targetVelocity) {
		double currentVelocity = getCurrentVelocity();
		if (currentVelocity < targetVelocity) {
				UpperShooterFalcon.selectMotionParameters(shooterMotionParameters);
		}
		else {
				UpperShooterFalcon.selectMotionParameters(slowShooterMotionParameters);
		}
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Upper Shooter Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Upper Shooter Target Velocity", this.getTargetVelocity());
		SmartDashboard.putNumber("Upper Shooter Velocity Error", this.getVelocityError());
		SmartDashboard.putNumber("Upper Shooter Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Upper Shooter Voltage", this.UpperShooterFalcon.getMotorOutputVoltage());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.UpperShooterFalcon.getSelectedSensorVelocity();
		return currentVelocity;
	}

	@Override
	public boolean isAtVelocity(double targetVelocity) {
		double currentVelocity = this.getCurrentVelocity();
		double VelocityError = Math.abs(currentVelocity - targetVelocity);
		if (VelocityError < onTargetThreshold) {
			return true;
		} else {
			return false;
		}
	}
}
