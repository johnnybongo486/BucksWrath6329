package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class Drivetrain extends SubsystemBase {

    private final TalonFX leftLead = new TalonFX(1);
    private final TalonFX leftFollow = new TalonFX(2);
    private final TalonFX rightLead = new TalonFX(3);
    private final TalonFX rightFollow = new TalonFX(4);
  
    private final TalonSRX spareTalon = new TalonSRX(5);
    private PigeonIMU pigeon = new PigeonIMU(spareTalon);

    // Set Motor Directions
    TalonFXInvertType rightInvert = TalonFXInvertType.CounterClockwise;
    TalonFXInvertType leftInvert = TalonFXInvertType.Clockwise;

    public TalonFXConfiguration leftConfig = new TalonFXConfiguration();
    public TalonFXConfiguration rightConfig = new TalonFXConfiguration();

    public double _leftOffset;
    public double _rightOffset;

    public double kP, kI, kD, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public double tkP, tkI, tkD, tkF, tkMaxOutput, tkMinOutput, tmaxRPM, tmaxVel, tminVel, tmaxAcc, tallowedErr;
    public int kIz, tkIz;

    public double distanceError = 0;
    public double targetDistance = 0;

    boolean _firstCall = false;
	boolean _state = false;
	double _targetAngle = 0;

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

        // PID coefficients
        kP = 0.079; 
        kI = 0;         
        kD = 0;         
        kIz = 12;       
        kF = 0.058; 
        kMaxOutput = 1; 
        kMinOutput = -1;
                
        tkP = 0.079; 
        tkI = 0;
        tkD = 0; 
        tkIz = 12; 
        tkF = 0.058; 
        tkMaxOutput = 1; 
        tkMinOutput = -1;
        
        maxRPM = 6380;

        // FPID for Distance 
        rightConfig.slot0.kF = kF;
        rightConfig.slot0.kP = kP;
        rightConfig.slot0.kI = kI;
        rightConfig.slot0.kD = kD;
        rightConfig.slot0.integralZone = kIz;
        rightConfig.slot0.closedLoopPeakOutput = kMaxOutput;
        leftConfig.slot0.kF = kF;
        leftConfig.slot0.kP = kP;
        leftConfig.slot0.kI = kI;
        leftConfig.slot0.kD = kD;
        leftConfig.slot0.integralZone = kIz;
        leftConfig.slot0.closedLoopPeakOutput = kMaxOutput;
        
        //FPID for Heading
        rightConfig.slot1.kF = tkF;
        rightConfig.slot1.kP = tkP;
        rightConfig.slot1.kI = tkI;
        rightConfig.slot1.kD = tkD;
        rightConfig.slot1.integralZone = tkIz;
        rightConfig.slot1.closedLoopPeakOutput = tkMaxOutput;
        leftConfig.slot1.kF = tkF;
        leftConfig.slot1.kP = tkP;
        leftConfig.slot1.kI = tkI;
        leftConfig.slot1.kD = tkD;
        leftConfig.slot1.integralZone = tkIz;
        leftConfig.slot1.closedLoopPeakOutput = tkMaxOutput;
        
        // Set Timeouts
        int closedLoopTimeMs = 1;
		rightConfig.slot0.closedLoopPeriod = closedLoopTimeMs;
		rightConfig.slot1.closedLoopPeriod = closedLoopTimeMs;
		rightConfig.slot2.closedLoopPeriod = closedLoopTimeMs;
        rightConfig.slot3.closedLoopPeriod = closedLoopTimeMs;
        leftConfig.slot0.closedLoopPeriod = closedLoopTimeMs;
		leftConfig.slot1.closedLoopPeriod = closedLoopTimeMs;
		leftConfig.slot2.closedLoopPeriod = closedLoopTimeMs;
        leftConfig.slot3.closedLoopPeriod = closedLoopTimeMs;

        // Motion Magic Configs 
        rightConfig.motionAcceleration = 8700; //(distance units per 100 ms) per second
        rightConfig.motionCruiseVelocity = 8700; //distance units per 100 ms // could be up to 21k

        leftConfig.motionAcceleration = 8700; //(distance units per 100 ms) per second
        leftConfig.motionCruiseVelocity = 8700; //distance units per 100 ms // could be up to 21k

        /* APPLY the config settings */
		leftLead.configAllSettings(leftConfig);
        rightLead.configAllSettings(rightConfig);

        /* Set status frame periods to ensure we don't have stale data */
		/* These aren't configs (they're not persistant) so we can set these after the configs.  */
		rightLead.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, 30);
		leftLead.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 30);
		pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR , 5, 30);

        rightLead.selectProfileSlot(0, 0);
        rightLead.selectProfileSlot(1, 1);
        leftLead.selectProfileSlot(0, 0);
        leftLead.selectProfileSlot(1, 1);
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

    public void stopDrivetrain() {
        this.leftLead.set(ControlMode.PercentOutput, 0);
        this.rightLead.set(ControlMode.PercentOutput, 0);
    }

    public void smartDrive (double leftRotations, double rightRotations) {
		this.leftLead.set(ControlMode.Position, leftRotations);
        this.rightLead.set(ControlMode.Position, rightRotations);
    }

    public void magicDrive (double distance) {
        this.rightLead.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
        this.leftLead.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
    }

    public void magicDriveAngle (double distance, double angle) {
        this.leftLead.follow(rightLead, FollowerType.AuxOutput1);
        this.rightLead.set(ControlMode.MotionMagic, distance, DemandType.AuxPID, angle);
    }

    public void drive(ControlMode controlMode, double left, double right) {
        this.leftLead.set(controlMode, left);
        this.rightLead.set(controlMode, right);
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

    public double getTargetDistance() {
        return targetDistance;
    }

    public double getDistanceError() {
        return distanceError;
    }

    public void setTargetDistance(double targetDistance) {
        this.targetDistance = targetDistance;
    }

    public void setDistanceError(double distanceError) {
        this.distanceError = distanceError;
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
        SmartDashboard.putNumber("Drivetrain / Target Distance", targetDistance);
        SmartDashboard.putNumber("Drivetrain / Distance Error", distanceError);
        SmartDashboard.putBoolean("Drivetrain / High Gear", isHighGear());
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
