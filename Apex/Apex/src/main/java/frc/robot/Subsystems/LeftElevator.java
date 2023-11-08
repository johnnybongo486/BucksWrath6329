package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Commands.Elevator.JoystickElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LeftElevator extends Subsystem implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private int homePosition = 0;
	private int climbPosition = -13500;
	private int raiseRobotPosition = -10000;
	private int maxUpTravelPosition = 0;

	public final static int ELEVATOR_UP = 0;
	public final static int ELEVATOR_DOWN = 1;

	public int upPositionLimit = maxUpTravelPosition;
	public int downPositionLimit = -14000;
	private int targetPosition = 0;
	private double arbitraryFeedForward = 0.0;

	private final static int onTargetThreshold = 100;
	
	private final SRXGains upGains = new SRXGains(ELEVATOR_UP, 0.4, 0, 0, 0, 100);//0.2, 0, 0, 0.05, 100
	private final SRXGains downGains = new SRXGains(ELEVATOR_DOWN, 0.4, 0, 0, 0, 100);//0.1, 0, 0, 0.05, 100

	//Uses PID values to go to a position
	private MotionParameters highGearUpMotionParameters = new MotionParameters(11000, 5500, upGains);// 4700
	private MotionParameters highGearDownMotionParameters = new MotionParameters(11000, 5000, downGains);
	
	private double peakOutputReverse = -1.0;
	
	private CustomTalonSRX leftElevatorESC = new CustomTalonSRX(5);

	
	public void initDefaultCommand() {
		setDefaultCommand(new JoystickElevator());
	}

	public LeftElevator() {
		this.leftElevatorESC.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0);
	
		this.leftElevatorESC.configForwardSoftLimitEnable(true);
		this.leftElevatorESC.configForwardSoftLimitThreshold(upPositionLimit);

		this.leftElevatorESC.configReverseSoftLimitEnable(true);
		this.leftElevatorESC.configReverseSoftLimitThreshold(downPositionLimit);

		this.leftElevatorESC.setInverted(false);
		this.leftElevatorESC.setSensorPhase(true);

		this.leftElevatorESC.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		this.leftElevatorESC.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

		this.leftElevatorESC.configMotionParameters(highGearUpMotionParameters);
        this.leftElevatorESC.configMotionParameters(highGearDownMotionParameters);

		this.leftElevatorESC.setNeutralMode(NeutralMode.Brake);
		this.leftElevatorESC.configClosedloopRamp(0.25);

		this.leftElevatorESC.configVoltageCompSaturation(11.5);
		this.leftElevatorESC.enableVoltageCompensation(true);

		this.leftElevatorESC.configPeakOutputReverse(peakOutputReverse);

		this.resetElevatorEncoder();
	}

	//sets control mode to motion magic
	public void setElevator(ControlMode controlMode, double signal) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		leftElevatorESC.set(controlMode, signal);
	}

	public void setElevator(ControlMode controlMode, double signal, DemandType demandType, double demand) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		leftElevatorESC.set(controlMode, signal, demandType, demand);
	}

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
		this.leftElevatorESC.set(ControlMode.MotionMagic, targetPosition, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
	}

	public int getCurrentPosition() {
		return this.leftElevatorESC.getSelectedSensorPosition();
	}

	public double getCurrentDraw() {
		return this.leftElevatorESC.getSupplyCurrent();
	}

	public boolean isHoldingPosition() {
		return this.isHoldingPosition;
	}

	public void setIsHoldingPosition(boolean isHoldingPosition) {
		this.isHoldingPosition = isHoldingPosition;
	}

	public int getTargetPosition() {
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

	public double getArbitraryFeedForward() {
		return this.arbitraryFeedForward;
	}

	public void resetElevatorEncoder() {
        try {
			leftElevatorESC.setSelectedSensorPosition(0, 0, 30);
        }
        catch (Exception e) {
            DriverStation.reportError("Elevator.resetElevatorEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
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
				leftElevatorESC.selectMotionParameters(highGearUpMotionParameters);
		}
		else {
				leftElevatorESC.selectMotionParameters(highGearDownMotionParameters);
		}
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Left Elevator Position", this.getCurrentPosition());
		SmartDashboard.putNumber("Left Elevator Target Position", this.getTargetPosition());
		SmartDashboard.putNumber("Left Elevator Position Error", this.getPositionError());
		SmartDashboard.putNumber("Left Elevator Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Left Elevator Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Left Elevator Voltage", this.leftElevatorESC.getMotorOutputVoltage());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.leftElevatorESC.getSelectedSensorVelocity();
		return currentVelocity;
	}

	@Override
	public boolean isInPosition(int targetPosition) {
		int currentPosition = this.getCurrentPosition();
		int positionError = Math.abs(currentPosition - targetPosition);
		if (positionError < onTargetThreshold) {
			return true;
		} else {
			return false;
		}
	}
}