package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Commands.Shooter.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem implements IVelocityControlledSubsystem {

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
	private MotionParameters shooterMotionParameters = new MotionParameters(5000, 20000, shooterGains);// velocity could be up to 21k
	private MotionParameters slowShooterMotionParameters = new MotionParameters(5000, 20000, slowShooterGains);// 4700

	
	private double peakOutputForward = 1.0;
	private double peakOutputReverse = -1.0;

	public CustomTalonFX ShooterFalcon = new CustomTalonFX(6);
	private CustomTalonFX ShooterFollower = new CustomTalonFX(7);


	public void initDefaultCommand() {
		setDefaultCommand(new JoystickShooter());
	}

	public Shooter() {
		this.ShooterFollower.follow(ShooterFalcon);
		this.ShooterFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

		this.ShooterFalcon.configForwardSoftLimitEnable(false);
		this.ShooterFalcon.configReverseSoftLimitEnable(false);

		this.ShooterFalcon.setInverted(true);
		this.ShooterFollower.setInverted(false);
		this.ShooterFalcon.setSensorPhase(true);

		this.ShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		this.ShooterFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
		
		this.ShooterFalcon.setNeutralMode(NeutralMode.Coast);
		this.ShooterFalcon.configClosedloopRamp(1);

		this.ShooterFalcon.configVoltageCompSaturation(11.5);
		this.ShooterFalcon.enableVoltageCompensation(true);

		this.ShooterFalcon.configPeakOutputReverse(peakOutputReverse, 30);
		this.ShooterFalcon.configPeakOutputForward(peakOutputForward, 30);

		// this.ShooterFalcon.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 30);
		// this.ShooterFalcon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);
		// this.ShooterFalcon.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 30);
		// this.ShooterFalcon.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, 30);

		this.ShooterFalcon.configMotionParameters(shooterMotionParameters);

		this.resetShooterEncoder();
	}

	//sets control mode to motion magic
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
	}

	public void velocityControl() {
		this.manageVelocity(targetVelocity);
		this.ShooterFalcon.set(ControlMode.Velocity, targetVelocity, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
	}

	public double getCurrentDraw() {
		return this.ShooterFalcon.getSupplyCurrent();
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
			ShooterFalcon.setSelectedSensorPosition(0, 0, 30);
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

	public void manageVelocity(double targetVelocity) {
		double currentVelocity = getCurrentVelocity();
		if (currentVelocity < targetVelocity) {
				ShooterFalcon.selectMotionParameters(shooterMotionParameters);
		}
		else {
				ShooterFalcon.selectMotionParameters(slowShooterMotionParameters);
		}
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Shooter Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Shooter Target Velocity", this.getTargetVelocity());
		SmartDashboard.putNumber("Shooter Velocity Error", this.getVelocityError());
		SmartDashboard.putNumber("Shooter Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Shooter Voltage", this.ShooterFalcon.getMotorOutputVoltage());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.ShooterFalcon.getSelectedSensorVelocity();
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