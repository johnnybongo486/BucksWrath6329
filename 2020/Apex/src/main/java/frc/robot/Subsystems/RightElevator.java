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

public class RightElevator extends Subsystem implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private int homePosition = 0;
	private int climbPosition = 14000;
	private int raiseRobotPosition = 10000;
	private int maxUpTravelPosition = 28000;

	public final static int ELEVATOR_UP = 0;
	public final static int ELEVATOR_DOWN = 1;

	public int upPositionLimit = maxUpTravelPosition;
	public int downPositionLimit = 0;
	private int targetPosition = 0;
	private double arbitraryFeedForward = 0.0;

	private final static int onTargetThreshold = 1000;
	
	private final SRXGains upGains = new SRXGains(ELEVATOR_UP, 0.4, 0, 0, 0, 100);//0.2, 0, 0, 0.05, 100
	private final SRXGains downGains = new SRXGains(ELEVATOR_DOWN, 0.4, 0, 0, 0, 100);//0.1, 0, 0, 0.05, 100

	
	//Uses PID values to go to a position
	private MotionParameters highGearUpMotionParameters = new MotionParameters(11000, 5500, upGains);// 4700
	private MotionParameters highGearDownMotionParameters = new MotionParameters(11000, 5000, downGains);
	
	private double peakOutputReverse = -1.0;
	
	private CustomTalonSRX rightElevatorESC = new CustomTalonSRX(6);
	
	public void initDefaultCommand() {
		setDefaultCommand(new JoystickElevator());
	}

	public RightElevator() {
		this.rightElevatorESC.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0);
	
		this.rightElevatorESC.configForwardSoftLimitEnable(true);
		this.rightElevatorESC.configForwardSoftLimitThreshold(upPositionLimit);

		this.rightElevatorESC.configReverseSoftLimitEnable(true);
		this.rightElevatorESC.configReverseSoftLimitThreshold(downPositionLimit);

		this.rightElevatorESC.setInverted(false);
		this.rightElevatorESC.setSensorPhase(true);

		this.rightElevatorESC.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		this.rightElevatorESC.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

		this.rightElevatorESC.configMotionParameters(highGearUpMotionParameters);
        this.rightElevatorESC.configMotionParameters(highGearDownMotionParameters);

		this.rightElevatorESC.setNeutralMode(NeutralMode.Brake);
		this.rightElevatorESC.configClosedloopRamp(0.25);

		this.rightElevatorESC.configVoltageCompSaturation(11.5);
		this.rightElevatorESC.enableVoltageCompensation(true);

		this.rightElevatorESC.configPeakOutputReverse(peakOutputReverse);

		this.resetElevatorEncoder();
	}

	//sets control mode to motion magic
	public void setElevator(ControlMode controlMode, double signal) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		rightElevatorESC.set(controlMode, signal);
	}

	public void setElevator(ControlMode controlMode, double signal, DemandType demandType, double demand) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		rightElevatorESC.set(controlMode, signal, demandType, demand);
	}

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
		this.rightElevatorESC.set(ControlMode.MotionMagic, targetPosition, DemandType.ArbitraryFeedForward, arbitraryFeedForward);
	}

	public int getCurrentPosition() {
		return this.rightElevatorESC.getSelectedSensorPosition();
	}

	public double getCurrentDraw() {
		return this.rightElevatorESC.getSupplyCurrent();
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
			rightElevatorESC.setSelectedSensorPosition(0, 0, 30);
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
				rightElevatorESC.selectMotionParameters(highGearUpMotionParameters);
		}
		else {
				rightElevatorESC.selectMotionParameters(highGearDownMotionParameters);
		}
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("Right Elevator Position", this.getCurrentPosition());
		SmartDashboard.putNumber("Right Elevator Target Position", this.getTargetPosition());
		SmartDashboard.putNumber("Right Elevator Position Error", this.getPositionError());
		SmartDashboard.putNumber("Right Elevator Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Right Elevator Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Right Elevator Voltage", this.rightElevatorESC.getMotorOutputVoltage());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.rightElevatorESC.getSelectedSensorVelocity();
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