package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Constants;
import frc.robot.Commands.Wrist.JoystickWrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends Subsystem implements IPositionControlledSubsystem {

	private boolean isHoldingPosition = false;

    // Set Different Heights
	private int homePosition = 0;
	private int hatchPosition = 1225;
	private int lvl1RocketPosition = 650;
	private int runIntakePosition = 1850;
	private int maxUpTravelPosition = 2000;
	private int cargoPosition = -1000;

	public final static int Wrist_UP = 0;
	public final static int Wrist_DOWN = 1;

	public int upPositionLimit = maxUpTravelPosition;
	public int downPositionLimit = -2800;
	public boolean isFullUp;
	private int targetPosition = 0; // changed from 0
	private double arbitraryFeedForward = 0.0;

	private boolean isHatchMode = false;

	private final static int onTargetThreshold = 50; 
	private final SRXGains upGains = new SRXGains(Wrist_UP, 1.5, 0, 5, 5, 50);  // changed d from 0.2 to 1
	private final SRXGains downGains = new SRXGains(Wrist_DOWN, 2, 0, 5, 5, 50);

	
	//Uses PID values to go to a position
	private MotionParameters highGearUpMotionParameters = new MotionParameters(5000, 2500, upGains);
	private MotionParameters highGearDownMotionParameters = new MotionParameters(5000, 2500, downGains);
	 
	private double peakOutputReverse = -1.0;

    private CustomTalonSRX WristLead = new CustomTalonSRX(1);

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickWrist());
	}

	public Wrist() {

		this.WristLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
	

		this.WristLead.configForwardSoftLimitEnable(true);
		this.WristLead.configForwardSoftLimitThreshold(upPositionLimit);

		this.WristLead.configReverseSoftLimitEnable(true);
		this.WristLead.configReverseSoftLimitThreshold(downPositionLimit);

		this.WristLead.setInverted(true);
		this.WristLead.setSensorPhase(false);

		this.WristLead.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
		this.WristLead.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

		this.WristLead.configMotionParameters(highGearUpMotionParameters);
        this.WristLead.configMotionParameters(highGearDownMotionParameters);

		this.WristLead.setNeutralMode(NeutralMode.Brake);
		this.WristLead.configClosedloopRamp(0.25);

		this.WristLead.configVoltageCompSaturation(11.5);
		this.WristLead.enableVoltageCompensation(true);

		this.WristLead.configPeakOutputReverse(peakOutputReverse);
		this.WristLead.configPeakOutputForward(0.8);

		this.resetWristEncoder();
	}

	//sets control mode to motion magic
	
	public void setIsFullUp(boolean boolie) {
		this.isFullUp = boolie;
	}
	
	public void setWrist(ControlMode controlMode, double signal) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		WristLead.set(controlMode, signal);
	}

	public void setWrist(ControlMode controlMode, double signal, DemandType demandType, double demand) {
		if (controlMode == ControlMode.MotionMagic) {
			this.manageMotion(signal);
		}
		WristLead.set(controlMode, signal, demandType, demand);
	}

	public void motionMagicControl() {
		this.manageMotion(targetPosition);
		this.WristLead.set(ControlMode.MotionMagic, targetPosition, DemandType.ArbitraryFeedForward, 0.1);
	}

	public int getCurrentPosition() {
		return this.WristLead.getSelectedSensorPosition();
	}

	public double getCurrentDraw() {
		return this.WristLead.getOutputCurrent();
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
	
	public boolean getIsFullUp() {
		return this.isFullUp;
	}

	public int getHomePosition() {
		return this.homePosition;
	}

	public int getHatchPosition() {
		return this.hatchPosition;
	}

	public int getcargoPosition() {
		return this.cargoPosition;
	}

	public int getLvl1RocketPosition() {
		return this.lvl1RocketPosition;
	}

	public int getMaxUpTravelPosition() {
		return this.maxUpTravelPosition;
	}


	public int getrunIntakePosition() {
		return this.runIntakePosition;
	}

	public double getArbitraryFeedForward() {
		return this.arbitraryFeedForward;
	}

	public void resetWristEncoder() {
        try {
			WristLead.setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
        }
        catch (Exception e) {
            DriverStation.reportError("Wrist.resetWristEncoders exception.  You're Screwed! : " + e.toString(), false);
        }
	}

	public void manageMotion(double targetPosition) {
		double currentPosition = getCurrentPosition();
		if (currentPosition < targetPosition) {
				WristLead.selectMotionParameters(highGearUpMotionParameters);
		}
		else {
				WristLead.selectMotionParameters(highGearDownMotionParameters);
		}
	}

	public boolean isHatchMode() {
        return isHatchMode;
    }

    public void setIsHatchMode(boolean isHatchMode) {
        this.isHatchMode = isHatchMode;
    }

	public void updateDashboard() {
		SmartDashboard.putNumber("Wrist / Wrist Position", this.getCurrentPosition());
		SmartDashboard.putBoolean("Wrist / Wrist FullUp", this.getIsFullUp());
		SmartDashboard.putNumber("Wrist / Wrist Velocity", this.getCurrentVelocity());
		SmartDashboard.putNumber("Wrist / Wrist Current", this.getCurrentDraw());
		SmartDashboard.putNumber("Wrist / Wrist Voltage", this.WristLead.getMotorOutputVoltage());
		SmartDashboard.putNumber("Wrist / Wrist Voltage", this.WristLead.getMotorOutputVoltage());
	}

	@Override
	public double getCurrentVelocity() {
		double currentVelocity = this.WristLead.getSelectedSensorVelocity();
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