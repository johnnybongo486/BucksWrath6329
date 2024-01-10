package frc.robot.subsystems;

import frc.lib.models.*;
import frc.robot.Robot;

import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RightShooter extends SubsystemBase implements IVelocityControlledSubsystem {

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

	private final static double onTargetThreshold = 50;
	
	private final SRXGains shooterGains = new SRXGains(Shooter_PIDX, 0.05, 0, 0, .05, 100);//0.2, 0, 0, 0.05, 100
	private final SRXGains slowShooterGains = new SRXGains(Shooter_PIDX, 0.05, 0, 0, 0, 100);//0.2, 0, 0, 0.05, 100

	//Uses PID values to go to a Velocity
	private MotionParameters shooterMotionParameters = new MotionParameters(10000, 20000, shooterGains);// velocity could be up to 21k
	private MotionParameters slowShooterMotionParameters = new MotionParameters(10000, 20000, slowShooterGains);// 4700

	
	private double peakOutputForward = 1.0;
	private double peakOutputReverse = -1.0;

	public CustomTalonFX ShooterFalcon = new CustomTalonFX(11);
	// Removing unused shooter follower for 2024 prototype
	// private CustomTalonFX ShooterFollower = new CustomTalonFX(12);

	public RightShooter() {
		TalonFXConfigurator shooterConfigurator = this.ShooterFalcon.getConfigurator();
		shooterConfigurator.apply(new TalonFXConfiguration());
		this.ShooterFalcon.clearStickyFaults();

		shooterConfigurator.apply(new FeedbackConfigs()
			.withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor));
		//this.ShooterFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

		shooterConfigurator.apply(new SoftwareLimitSwitchConfigs()
			.withForwardSoftLimitEnable(false)
			.withReverseSoftLimitEnable(false));
		//this.ShooterFalcon.configForwardSoftLimitEnable(false);
		//this.ShooterFalcon.configReverseSoftLimitEnable(false);

		this.ShooterFalcon.setInverted(true);
		// Unused and unnecessary
		//this.ShooterFalcon.setSensorPhase(true);

		//TODO: I could not identify the right thing to replace these.
		//this.ShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		//this.ShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
		
		this.ShooterFalcon.setNeutralMode(NeutralModeValue.Coast);
		shooterConfigurator.apply(new ClosedLoopRampsConfigs()
			.withDutyCycleClosedLoopRampPeriod(1)
			.withTorqueClosedLoopRampPeriod(1)
			.withVoltageClosedLoopRampPeriod(1));
		//this.ShooterFalcon.configClosedloopRamp(1);

		//TODO: I could not identify the right thing to replace these.
		/*this.ShooterFalcon.configVoltageCompSaturation(11.5);
		this.ShooterFalcon.enableVoltageCompensation(true);

		this.ShooterFalcon.configPeakOutputReverse(peakOutputReverse, 30);
		this.ShooterFalcon.configPeakOutputForward(peakOutputForward, 30);*/

		this.ShooterFalcon.configMotionParameters(shooterMotionParameters);

		this.resetShooterEncoder();
	}

	/*//sets control mode to motion magic
	public void setShooter(ControlMode controlMode, double signal) {
		if (controlMode == ControlMode.Velocity) {
			this.manageVelocity(signal);
		}
		ShooterFalcon.set(controlMode, signal);
	}

	public void setShooter(ControlMode controlMode, double signal, DemandType demandType, double demand) {
		if (controlMode == ControlMode.Velocity) {
			this.manageVelocity(signal);
		}
		ShooterFalcon.set(controlMode, signal, demandType, demand);
	}*/

	public void velocityControl() {
		this.manageVelocity(targetVelocity);
		//this.ShooterFalcon.set(ControlMode.Velocity, targetVelocity, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
		this.ShooterFalcon.setControl(new VelocityVoltage(targetVelocity));
	}

	//TODO: Removing for now
	/*public double getCurrentDraw() {
		return this.ShooterFalcon.getSupplyCurrent();
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
			ShooterFalcon.getConfigurator().setPosition(0, .03);
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
		value = -Robot.m_robotContainer.getOperatorLeftStickY();
		return value;
	}

	public void manageVelocity(double targetVelocity) {
		double currentVelocity = getCurrentVelocity();
		if (currentVelocity < targetVelocity) {
				ShooterFalcon.selectMotionParameters(shooterMotionParameters);
		}
		else {
				ShooterFalcon.selectMotionParameters(slowShooterMotionParameters);
		}
	}

	// Temporarily removing for prototyping
	/*public void updateDashboard() {
		SmartDashboard.putNumber("Shooter Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Shooter Target Velocity", this.getTargetVelocity());
		SmartDashboard.putNumber("Shooter Velocity Error", this.getVelocityError());
		SmartDashboard.putNumber("Shooter Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Shooter Voltage", this.ShooterFalcon.getMotorOutputVoltage());
	}*/

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.ShooterFalcon.getVelocity().getValueAsDouble();
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