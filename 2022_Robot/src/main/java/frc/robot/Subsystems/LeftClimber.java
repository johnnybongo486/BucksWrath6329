package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LeftClimber extends SubsystemBase implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private int homePosition = 0;
	private int climbPosition = 28000;
	private int raiseRobotPosition = 0;
	private int maxUpTravelPosition = 30000;
	private int nextBarPosition = 28000;

	public final static int Climber_UP = 0;
	public final static int Climber_DOWN = 1;

	public int upPositionLimit = maxUpTravelPosition;
	public int downPositionLimit = -1;
	private int targetPosition = 0;
	private double arbitraryFeedForward = 0.0;

	private final static int onTargetThreshold = 100;
	
	private final SRXGains upGains = new SRXGains(Climber_UP, 0.3, 0, 0, 0.011, 100);//0.2, 0, 0, 0.05, 100
	private final SRXGains downGains = new SRXGains(Climber_DOWN, 1.0, 0, 0, 0.011, 100);//0.1, 0, 0, 0.05, 100

	//Uses PID values to go to a position
	private MotionParameters highGearUpMotionParameters = new MotionParameters(180000, 90000, upGains);
	private MotionParameters highGearDownMotionParameters = new MotionParameters(180000, 90000, downGains);
	
	private double peakOutputReverse = -1.0;
	
	private CustomTalonSRX leftClimberESC = new CustomTalonSRX(9);

	public LeftClimber() {
		leftClimberESC.configFactoryDefault();
		leftClimberESC.clearStickyFaults();

		this.leftClimberESC.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0);
	
		this.leftClimberESC.configForwardSoftLimitEnable(true);
		this.leftClimberESC.configForwardSoftLimitThreshold(upPositionLimit);

		this.leftClimberESC.configReverseSoftLimitEnable(true);
		this.leftClimberESC.configReverseSoftLimitThreshold(downPositionLimit);

		this.leftClimberESC.setInverted(false);
		this.leftClimberESC.setSensorPhase(false);

		this.leftClimberESC.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		this.leftClimberESC.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

		this.leftClimberESC.configMotionParameters(highGearUpMotionParameters);
        this.leftClimberESC.configMotionParameters(highGearDownMotionParameters);

		this.leftClimberESC.setNeutralMode(NeutralMode.Brake);
		this.leftClimberESC.configClosedloopRamp(0.25);

		this.leftClimberESC.configVoltageCompSaturation(11.5);
		this.leftClimberESC.enableVoltageCompensation(true);

		this.leftClimberESC.configPeakOutputReverse(peakOutputReverse);

		this.resetClimberEncoder();
	}

	//sets control mode to motion magic
	public void setClimber(ControlMode controlMode, double signal) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		leftClimberESC.set(controlMode, signal);
	}

	public void setClimber(ControlMode controlMode, double signal, DemandType demandType, double demand) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		leftClimberESC.set(controlMode, signal, demandType, demand);
	}

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
		this.leftClimberESC.set(ControlMode.MotionMagic, targetPosition, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
	}

	public double getCurrentPosition() {
		return this.leftClimberESC.getSelectedSensorPosition();
	}

	public double getCurrentDraw() {
		return this.leftClimberESC.getSupplyCurrent();
	}

	public boolean isHoldingPosition() {
		return this.isHoldingPosition;
	}

	public void setIsHoldingPosition(boolean isHoldingPosition) {
		this.isHoldingPosition = isHoldingPosition;
	}

	public double getTargetPosition() {
		return this.targetPosition;
	}

	public boolean setTargetPosition(int position) {
		if (!isValidPosition(position)) {
			return false;
		} else {
			this.targetPosition = position;
			return true;
		}
	}

	public void forceSetTargetPosition(int position) {
		this.targetPosition = position;
	}

	public void incrementTargetPosition(int increment) {
		int currentTargetPosition = this.targetPosition;
		int newTargetPosition = currentTargetPosition + increment;
		if (isValidPosition(newTargetPosition)) {		// && isWristSafe(newTargetPosition) check for other subsystems
			this.targetPosition = newTargetPosition;
		}
	}

	public boolean isValidPosition(int position) {
		boolean withinBounds = position <= upPositionLimit && position >= downPositionLimit;
		return withinBounds;
	}

    // communicate with commands
	public int getHomePosition() {
		return this.homePosition;
	}

	public int getMaxUpTravelPosition() {
		return this.maxUpTravelPosition;
	}

	public int getClimbPosition() {
		return this.climbPosition;
	}

	public int getRaiseRobotPosition() {
		return this.raiseRobotPosition;
	}

	public int getNextBarPosition() {
		return this.nextBarPosition;
	}

	public double getArbitraryFeedForward() {
		return this.arbitraryFeedForward;
	}

	public void resetClimberEncoder() {
        try {
			leftClimberESC.setSelectedSensorPosition(0, 0, 30);
        }
        catch (Exception e) {
            DriverStation.reportError("Climber.resetClimberEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public double leftJoyStickClimber(){
		double value = 0;
		value = Robot.robotContainer.getOperatorLeftStickY();
		return value;
	}

	public double getPositionError() {
		double currentPosition = this.getCurrentPosition();
		double targetPosition = this.getTargetPosition();
		double positionError = Math.abs(currentPosition - targetPosition);
		return positionError;
	}

	public void manageMotion(double targetPosition) {
		double currentPosition = getCurrentPosition();
		if (currentPosition < targetPosition) {
				leftClimberESC.selectMotionParameters(highGearUpMotionParameters);
		}
		else {
				leftClimberESC.selectMotionParameters(highGearDownMotionParameters);
		}
	}

	public void zeroTarget() {
		targetPosition = 0;
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Left Climber Position", this.getCurrentPosition());
		SmartDashboard.putNumber("Left Climber Target Position", this.getTargetPosition());
		SmartDashboard.putNumber("Left Climber Position Error", this.getPositionError());
		SmartDashboard.putNumber("Left Climber Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Left Climber Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Left Climber Voltage", this.leftClimberESC.getMotorOutputVoltage());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.leftClimberESC.getSelectedSensorVelocity();
		return currentVelocity;
	}

	@Override
	public boolean isInPosition(int targetPosition) {
		double currentPosition = this.getCurrentPosition();
		double positionError = Math.abs(currentPosition - targetPosition);
		if (positionError < onTargetThreshold) {
			return true;
		} else {
			return false;
		}
	}
}
