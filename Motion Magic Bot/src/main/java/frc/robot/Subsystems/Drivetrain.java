package frc.robot.Subsystems;

import frc.robot.Commands.Drivetrain.JoystickDrive;
import frc.robot.Models.DriveSignal;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

public class Drivetrain extends Subsystem {

    public boolean isRed;
    public static int DRIVE_PROFILE = 0;
    public static int ROTATION_PROFILE = 1;

    public TalonFX leftLead = new TalonFX(2);
    public TalonFX rightLead = new TalonFX(3);
    public TalonFX leftFollower = new TalonFX(4);
    public TalonFX rightFollower = new TalonFX(5);
    public TalonSRX spareTalon = new TalonSRX(9);

    public PigeonIMU pigeon = new PigeonIMU(spareTalon);
    
    // Motion Magic Stuff
    public TalonFXConfiguration leftConfig = new TalonFXConfiguration();
    public TalonFXConfiguration rightConfig = new TalonFXConfiguration();
    TalonFXInvertType rightInvert = TalonFXInvertType.Clockwise;
    TalonFXInvertType leftInvert = TalonFXInvertType.CounterClockwise;

    public double _leftOffset;
    public double _rightOffset;

    /** Tracking variables */
	boolean _firstCall = false;
	boolean _state = false;
	double _targetAngle = 0;

	/** How much smoothing [0,8] to use during MotionMagic */
	int _smoothing;

    public void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    public Drivetrain() {
        // Setup Followers
        leftFollower.follow(leftLead);
        rightFollower.follow(rightLead);

        leftLead.setInverted(leftInvert);
		rightLead.setInverted(rightInvert);

        // Set Sensors
        leftLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        rightLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);

        // not needed stuff for motion magic
        leftConfig.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice();
        rightConfig.remoteFilter0.remoteSensorDeviceID = leftLead.getDeviceID();
        rightConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.TalonFX_SelectedSensor;
        setRobotDistanceConfigs(rightInvert, rightConfig);
        
        /** Heading Configs */
		rightConfig.remoteFilter1.remoteSensorDeviceID = pigeon.getDeviceID();    //Pigeon Device ID
		rightConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw; //This is for a Pigeon over CAN
		rightConfig.auxiliaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.RemoteSensor1.toFeedbackDevice(); //Set as the Aux Sensor
		rightConfig.auxiliaryPID.selectedFeedbackCoefficient = 3600.0 / 8192; //Convert Yaw to tenths of a degree

        int closedLoopTimeMs = 1;
		rightConfig.slot0.closedLoopPeriod = closedLoopTimeMs;
		rightConfig.slot1.closedLoopPeriod = closedLoopTimeMs;
		rightConfig.slot2.closedLoopPeriod = closedLoopTimeMs;
        rightConfig.slot3.closedLoopPeriod = closedLoopTimeMs;

        /* APPLY the config settings */
		leftLead.configAllSettings(leftConfig);
        rightLead.configAllSettings(rightConfig);
        
        /* Set status frame periods to ensure we don't have stale data */
		/* These aren't configs (they're not persistant) so we can set these after the configs.  */
		rightLead.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 30);
		pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR , 5, 30);
        
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

    public void magicDrive (double distance, double angle) {
        rightLead.set(ControlMode.MotionMagic, distance, DemandType.AuxPID, angle);
		leftLead.follow(rightLead, FollowerType.AuxOutput1);
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
        leftLead.getSensorCollection().setIntegratedSensorPosition(0, 10);
        rightLead.getSensorCollection().setIntegratedSensorPosition(0, 10);

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

    public boolean getIsRed(){
        return isRed;
    }

    public void setIsRed(boolean isRed) {
        this.isRed = isRed;
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Drivetrain / Left Lead Current", leftLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Lead Current", rightLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Speed", getLeftSpeed());
        SmartDashboard.putNumber("Drivetrain / Right Speed", getRightSpeed());
        SmartDashboard.putNumber("Drivetrain / Current Angle", getAngle());
        SmartDashboard.putNumber("Drivetrain / Current Angular Rate", getRoll());
    }

    void setRobotDistanceConfigs(TalonFXInvertType masterInvertType, TalonFXConfiguration masterConfig){
		/**
		 * Determine if we need a Sum or Difference.
		 * 
		 * The auxiliary Talon FX will always be positive
		 * in the forward direction because it's a selected sensor
		 * over the CAN bus.
		 * 
		 * The master's native integrated sensor may not always be positive when forward because
		 * sensor phase is only applied to *Selected Sensors*, not native
		 * sensor sources.  And we need the native to be combined with the 
		 * aux (other side's) distance into a single robot distance.
		 */

		/* THIS FUNCTION should not need to be modified. 
		   This setup will work regardless of whether the master
		   is on the Right or Left side since it only deals with
		   distance magnitude.  */

		/* Check if we're inverted */
		if (masterInvertType == TalonFXInvertType.Clockwise){
			/* 
				If master is inverted, that means the integrated sensor
				will be negative in the forward direction.
				If master is inverted, the final sum/diff result will also be inverted.
				This is how Talon FX corrects the sensor phase when inverting 
				the motor direction.  This inversion applies to the *Selected Sensor*,
				not the native value.
				Will a sensor sum or difference give us a positive total magnitude?
				Remember the Master is one side of your drivetrain distance and 
				Auxiliary is the other side's distance.
					Phase | Term 0   |   Term 1  | Result
				Sum:  -1 *((-)Master + (+)Aux   )| NOT OK, will cancel each other out
				Diff: -1 *((-)Master - (+)Aux   )| OK - This is what we want, magnitude will be correct and positive.
				Diff: -1 *((+)Aux    - (-)Master)| NOT OK, magnitude will be correct but negative
			*/

			masterConfig.diff0Term = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice(); //Local Integrated Sensor
			masterConfig.diff1Term = TalonFXFeedbackDevice.RemoteSensor0.toFeedbackDevice();   //Aux Selected Sensor
			masterConfig.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.SensorDifference.toFeedbackDevice(); //Diff0 - Diff1
		} else {
			/* Master is not inverted, both sides are positive so we can sum them. */
			masterConfig.sum0Term = TalonFXFeedbackDevice.RemoteSensor0.toFeedbackDevice();    //Aux Selected Sensor
			masterConfig.sum1Term = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice(); //Local IntegratedSensor
			masterConfig.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.SensorSum.toFeedbackDevice(); //Sum0 + Sum1
		}

		/* Since the Distance is the sum of the two sides, divide by 2 so the total isn't double
		   the real-world value */
		masterConfig.primaryPID.selectedFeedbackCoefficient = 0.5;
	 }
}
