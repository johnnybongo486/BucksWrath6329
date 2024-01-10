package frc.robot.subsystems;

import frc.robot.Robot;
import frc.lib.models.*;

import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LeftShooter extends SubsystemBase implements IVelocityControlledSubsystem {

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

	public CustomTalonFX LeftShooterFalcon = new CustomTalonFX(12);

	public LeftShooter() {
		TalonFXConfigurator m_ShooterConfigurator = this.LeftShooterFalcon.getConfigurator();
		m_ShooterConfigurator.apply(new TalonFXConfiguration());
		this.LeftShooterFalcon.clearStickyFaults();

		m_ShooterConfigurator.apply(new FeedbackConfigs()
			.withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor));
		//this.LeftShooterFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

		m_ShooterConfigurator.apply(new SoftwareLimitSwitchConfigs()
			.withForwardSoftLimitEnable(false)
			.withReverseSoftLimitEnable(false));

		this.LeftShooterFalcon.setInverted(false);

		//this.LeftShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		//this.LeftShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
		// slow down the position signal to 5 Hz
		// new version if we need tm_talonFX.getPosition().setUpdateFrequency(5);
		
		this.LeftShooterFalcon.setNeutralMode(NeutralModeValue.Coast);
		m_ShooterConfigurator.apply(new ClosedLoopRampsConfigs()
			.withDutyCycleClosedLoopRampPeriod(1)
			.withTorqueClosedLoopRampPeriod(1)
			.withVoltageClosedLoopRampPeriod(1));
		//this.LeftShooterFalcon.configClosedloopRamp(1);

		this.LeftShooterFalcon.configMotionParameters(shooterMotionParameters);

		this.resetShooterEncoder();
	}

	/*//sets control mode to motion magic
	public void setShooter(ControlMode controlMode, double signal) {
		if (controlMode == ControlMode.Velocity) {
			this.manageVelocity(signal);
		}
		LeftShooterFalcon.set(controlMode, signal);
	}

	public void setShooter(ControlMode controlMode, double signal, DemandType demandType, double demand) {
		if (controlMode == ControlMode.Velocity) {
			this.manageVelocity(signal);
		}
		LeftShooterFalcon.set(controlMode, signal, demandType, demand);
	}*/
	// new version

	public void setShooter(ControlModeValue dutyCycle, double signal) {
		if (dutyCycle == ControlModeValue.MotionMagicVelocityDutyCycle) {
			this.manageVelocity(signal);
		}
		//LeftShooterFalcon.setControl(dutyCycle, signal);
	}

	public void velocityControl() {
		this.manageVelocity(targetVelocity);
		//this.LeftShooterFalcon.set(ControlMode.Velocity, targetVelocity, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
		this.LeftShooterFalcon.setControl(new VelocityVoltage(targetVelocity));
		this.LeftShooterFalcon.setControl(new 
	}

	//TODO: Removing for now
	/*public double getCurrentDraw() {
		return this.LeftShooterFalcon.getSupplyCurrent();
	}*/

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
			LeftShooterFalcon.getConfigurator().setPosition(0, .03);
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
				LeftShooterFalcon.selectMotionParameters(shooterMotionParameters);
		}
		else {
				LeftShooterFalcon.selectMotionParameters(slowShooterMotionParameters);
		}
	}

	/*public void updateDashboard() {
		SmartDashboard.putNumber("Upper Shooter Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Upper Shooter Target Velocity", this.getTargetVelocity());
		SmartDashboard.putNumber("Upper Shooter Velocity Error", this.getVelocityError());
		SmartDashboard.putNumber("Upper Shooter Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Upper Shooter Voltage", this.LeftShooterFalcon.getMotorOutputVoltage());
	}*/

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.LeftShooterFalcon.getVelocity().getValueAsDouble();
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