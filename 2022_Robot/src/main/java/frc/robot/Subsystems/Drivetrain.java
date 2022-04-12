package frc.robot.Subsystems;

import frc.robot.Models.DriveSignal;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

public class Drivetrain extends SubsystemBase {

    public TalonFX leftLead = new TalonFX(1);
    public TalonFX leftFollower = new TalonFX(2);
    public TalonFX leftTopFollower = new TalonFX(3);
    public TalonFX rightLead = new TalonFX(14);
    public TalonFX rightFollower = new TalonFX(15);
    public TalonFX rightTopFollower = new TalonFX(16);

    public PigeonIMU pigeon = new PigeonIMU(0);
    
    // Motion Magic Stuff
    public TalonFXConfiguration leftConfig = new TalonFXConfiguration();
    public TalonFXConfiguration rightConfig = new TalonFXConfiguration();
    TalonFXInvertType rightInvert = TalonFXInvertType.Clockwise;
    TalonFXInvertType leftInvert = TalonFXInvertType.CounterClockwise;

    public double _leftOffset;
    public double _rightOffset;

    private boolean isHighGear = true;

    public double kP, kI, kD, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public double tkP, tkI, tkD, tkF, tkMaxOutput, tkMinOutput, tmaxRPM, tmaxVel, tminVel, tmaxAcc, tallowedErr;
    public int kIz, tkIz;

    public double distanceError = 0;
    public double targetDistance = 0;

    public Drivetrain() {
        leftLead.configFactoryDefault(10);
        leftFollower.configFactoryDefault(10);
        leftTopFollower.configFactoryDefault(10);
        rightLead.configFactoryDefault(10);
        rightFollower.configFactoryDefault(10);
        rightTopFollower.configFactoryDefault(10);

        leftLead.clearStickyFaults(10);
        leftFollower.clearStickyFaults(10);
        leftTopFollower.clearStickyFaults(10);
        rightLead.clearStickyFaults(10);
        rightFollower.clearStickyFaults(10);
        rightTopFollower.clearStickyFaults(10);

        leftLead.setInverted(leftInvert);
        leftFollower.setInverted(leftInvert);
        leftTopFollower.setInverted(leftInvert);
        rightLead.setInverted(rightInvert);
        rightFollower.setInverted(rightInvert);
        rightTopFollower.setInverted(rightInvert);

        leftLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        rightLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        leftFollower.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        leftTopFollower.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        rightFollower.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        rightTopFollower.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        leftLead.setSensorPhase(false);
        leftFollower.setSensorPhase(false);
        leftTopFollower.setSensorPhase(false);
        rightLead.setSensorPhase(false);
        rightFollower.setSensorPhase(false);
        rightTopFollower.setSensorPhase(false);

        resetPigeon();
        setNeutralMode(NeutralMode.Brake);

        // PID coefficients
        kP = 0.11; 
        kI = 0;         // .000005
        kD = 0;         // .05
        kIz = 12;       // 12
        kF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;
                
        tkP = 0.1; 
        tkI = 0;
        tkD = 0; 
        tkIz = 12; 
        tkF = 0; 
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
        rightConfig.motionAcceleration = 14000; //(distance units per 100 ms) per second
        rightConfig.motionCruiseVelocity = 21000; //distance units per 100 ms // could be up to 21k

        leftConfig.motionAcceleration = 14000; //(distance units per 100 ms) per second
        leftConfig.motionCruiseVelocity = 21000; //distance units per 100 ms // could be up to 21k

        /* APPLY the config settings */
		leftLead.configAllSettings(leftConfig);
        leftFollower.configAllSettings(leftConfig);
        leftTopFollower.configAllSettings(leftConfig);
        rightLead.configAllSettings(rightConfig);
        rightFollower.configAllSettings(rightConfig);
        rightTopFollower.configAllSettings(rightConfig);
        
        /* Set status frame periods to ensure we don't have stale data */
		// These aren't configs (they're not persistant) so we can set these after the configs.  
		rightLead.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 30);
		rightLead.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, 30);
		leftLead.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 30);
		pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR , 5, 30);

        /**
         * Configure the current limits that will be used Stator Current is the current
         * that passes through the motor stators. Use stator current limits to limit
         * rotor acceleration/heat production Supply Current is the current that passes
         * into the controller from the supply Use supply current limits to prevent
         * breakers from tripping
         *                                                                          enabled | Limit(amp) | Trigger Threshold(amp) | Trigger Threshold Time(s) */
        leftLead.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(      true,       40,                 70,                 0.25));
        leftLead.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(      true,       40,                 70,                 0.25));
        leftFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(  true,       40,                 70,                 0.25));
        leftFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(  true,       40,                 70,                 0.25));
        leftTopFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(  true,       40,                 70,                 0.25));
        leftTopFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(  true,       40,                 70,                 0.25));

        rightLead.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(     true,       40,                 70,                 0.25));
        rightLead.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(     true,       40,                 70,                 0.25));
        rightFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration( true,       40,                 70,                 0.25));
        rightFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration( true,       40,                 70,                 0.25));
        rightTopFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration( true,       40,                 70,                 0.25));
        rightTopFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration( true,       40,                 70,                 0.25));

        // Set Ramp Rates
        leftLead.configOpenloopRamp(0.25);
        leftFollower.configOpenloopRamp(0.25);
        leftTopFollower.configOpenloopRamp(0.25);
        rightLead.configOpenloopRamp(0.25);
        rightFollower.configOpenloopRamp(0.25);
        rightTopFollower.configOpenloopRamp(0.25);

        leftLead.configClosedloopRamp(0.25);
        leftFollower.configClosedloopRamp(0.25);
        leftTopFollower.configClosedloopRamp(0.25);
        rightLead.configClosedloopRamp(0.25);
        rightFollower.configClosedloopRamp(0.25);
        rightTopFollower.configClosedloopRamp(0.25);

        leftLead.configClosedLoopPeakOutput(0, 1.0);
        leftFollower.configClosedLoopPeakOutput(0, 1.0);
        leftTopFollower.configClosedLoopPeakOutput(0, 1.0);
        rightLead.configClosedLoopPeakOutput(0, 1.0);
        rightFollower.configClosedLoopPeakOutput(0, 1.0);
        rightTopFollower.configClosedLoopPeakOutput(0, 1.0);


        leftLead.configPeakOutputForward(1.0);
        leftFollower.configPeakOutputForward(1.0);
        leftTopFollower.configPeakOutputForward(1.0);
        rightLead.configPeakOutputForward(1.0);
        rightFollower.configPeakOutputForward(1.0);
        rightTopFollower.configPeakOutputForward(1.0);


        leftLead.configPeakOutputReverse(-1.0);
        leftFollower.configPeakOutputReverse(-1.0);
        leftTopFollower.configPeakOutputReverse(-1.0);
        rightLead.configPeakOutputReverse(-1.0);
        rightFollower.configPeakOutputReverse(-1.0);
        rightTopFollower.configPeakOutputReverse(-1.0);

        leftLead.configVoltageCompSaturation(11);
        leftFollower.configVoltageCompSaturation(11);
        leftTopFollower.configVoltageCompSaturation(11);
        rightLead.configVoltageCompSaturation(11);
        rightFollower.configVoltageCompSaturation(11);
        rightTopFollower.configVoltageCompSaturation(11);

        leftLead.enableVoltageCompensation(false);
        rightLead.enableVoltageCompensation(false);
        leftFollower.enableVoltageCompensation(false);
        leftTopFollower.enableVoltageCompensation(false);
        rightFollower.enableVoltageCompensation(false);
        rightTopFollower.enableVoltageCompensation(false);

        rightLead.selectProfileSlot(0, 0);
        rightLead.selectProfileSlot(1, 1);
        rightFollower.selectProfileSlot(0, 0);
        rightFollower.selectProfileSlot(1, 1);
        rightTopFollower.selectProfileSlot(0, 0);
        rightTopFollower.selectProfileSlot(1, 1);
        leftLead.selectProfileSlot(0, 0);
        leftLead.selectProfileSlot(1, 1);
        leftFollower.selectProfileSlot(0, 0);
        leftFollower.selectProfileSlot(1, 1);
        leftTopFollower.selectProfileSlot(0, 0);
        leftTopFollower.selectProfileSlot(1, 1);

    }

    public void drive(ControlMode controlMode, double left, double leftFollow, double leftTopFollow, double right, double rightFollow, double rightTopFollow) {
        this.leftLead.set(controlMode, left);
        this.leftFollower.set(controlMode, leftFollow);
        this.leftTopFollower.set(controlMode, leftTopFollow);
        this.rightLead.set(controlMode, right);
        this.rightFollower.set(controlMode, rightFollow);
        this.rightTopFollower.set(controlMode, rightTopFollow);
    }

    public void stopDrivetrain() {
        this.leftLead.set(ControlMode.PercentOutput, 0);
        this.leftFollower.set(ControlMode.PercentOutput, 0);
        this.leftTopFollower.set(ControlMode.PercentOutput, 0);
        this.rightLead.set(ControlMode.PercentOutput, 0);
        this.rightFollower.set(ControlMode.PercentOutput, 0);
        this.rightTopFollower.set(ControlMode.PercentOutput, 0);
    }

    public void setDrive(ControlMode controlMode, DriveSignal driveSignal) {
        this.drive(controlMode, driveSignal.getLeft(), driveSignal.getLeftFollow(), driveSignal.getLeftTopFollow(), driveSignal.getRight(), driveSignal.getRightFollow(), driveSignal.getRightTopFollow());
    }

    public void magicDrive (double distance) {
        this.leftLead.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
        this.leftFollower.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
        this.leftTopFollower.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
        this.rightLead.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
        this.rightFollower.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
        this.rightTopFollower.set(ControlMode.MotionMagic, distance, DemandType.Neutral, 0.0);
    }

    public void magicTurn (double distanceLeft, double distanceRight) {
        this.leftLead.set(ControlMode.MotionMagic, distanceLeft, DemandType.Neutral, 0.0);
        this.leftFollower.set(ControlMode.MotionMagic, distanceLeft, DemandType.Neutral, 0.0);
        this.leftTopFollower.set(ControlMode.MotionMagic, distanceLeft, DemandType.Neutral, 0.0);
        this.rightLead.set(ControlMode.MotionMagic, distanceRight, DemandType.Neutral, 0.0);
        this.rightFollower.set(ControlMode.MotionMagic, distanceRight, DemandType.Neutral, 0.0);
        this.rightTopFollower.set(ControlMode.MotionMagic, distanceRight, DemandType.Neutral, 0.0);
    }

    public void setNeutralMode(NeutralMode neutralMode) {
        this.leftLead.setNeutralMode(neutralMode);
        this.leftFollower.setNeutralMode(neutralMode);
        this.leftTopFollower.setNeutralMode(neutralMode);
        this.rightLead.setNeutralMode(neutralMode);
        this.rightFollower.setNeutralMode(neutralMode);
        this.rightTopFollower.setNeutralMode(neutralMode);
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
        leftFollower.getSensorCollection().setIntegratedSensorPosition(0, 10);
        leftTopFollower.getSensorCollection().setIntegratedSensorPosition(0, 10);
        rightLead.getSensorCollection().setIntegratedSensorPosition(0, 10);
        rightFollower.getSensorCollection().setIntegratedSensorPosition(0, 10);
        rightTopFollower.getSensorCollection().setIntegratedSensorPosition(0, 10);
    }

    public double getRightDistance() {
        double rightLeadTicks = rightLead.getSelectedSensorPosition();
        double rightFollowTicks = rightFollower.getSelectedSensorPosition();
        double rightTopFollowTicks = rightTopFollower.getSelectedSensorPosition();
        double rightDistanceTotal = (rightLeadTicks + rightTopFollowTicks + rightFollowTicks) / 3;
        return rightDistanceTotal;
    }

    public double getLeftDistance() {
        double leftLeadTicks = leftLead.getSelectedSensorPosition();
        double leftFollowTicks = leftFollower.getSelectedSensorPosition();
        double leftTopFollowTicks = leftFollower.getSelectedSensorPosition();
        double leftDistanceTotal = (leftLeadTicks + leftTopFollowTicks + leftFollowTicks) / 3;
        return leftDistanceTotal;
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

    public boolean getIsHighGear() {
        return isHighGear;
    }

    public void setIsHighGear(boolean gear){
        this.isHighGear = gear;
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Drivetrain / Left Lead Current", leftLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Follow Current", leftFollower.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Top Follow Current", leftTopFollower.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Lead Current", rightLead.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Follow Current", rightFollower.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Top Follow Current", rightTopFollower.getSupplyCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Speed", getLeftSpeed());
        SmartDashboard.putNumber("Drivetrain / Right Speed", getRightSpeed());
        SmartDashboard.putNumber("Drivetrain / Current Angle", getAngle());
        SmartDashboard.putNumber("Drivetrain / Current Angular Rate", getRoll());
        SmartDashboard.putNumber("Drivetrain / Left Distance", leftLead.getSelectedSensorPosition());
        SmartDashboard.putNumber("Drivetrain / Right Distance", rightLead.getSelectedSensorPosition());
        SmartDashboard.putNumber("Drivetrain / Target Distance", targetDistance);
        SmartDashboard.putNumber("Drivetrain / Distance Error", distanceError);
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