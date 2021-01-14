package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Commands.Drivetrain.JoystickDrive;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drivetrain extends Subsystem {
    //speed controler def.
	private CANSparkMax leftFrontFollower = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless); 
    public CANSparkMax leftLeader = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax leftRearFollower = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax rightFrontFollower= new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    public CANSparkMax rightLeader = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
	private CANSparkMax rightRearFollower = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
	
	public TalonSRX spareTalon = new TalonSRX(11);

	// Encoders
	public CANEncoder leftLeaderEncoder = new CANEncoder(leftLeader);
	public CANEncoder rightLeaderEncoder = new CANEncoder(rightLeader);
	
    private PigeonIMU pigeon = new PigeonIMU(spareTalon);

	private boolean isHighGear = false;

	public CANPIDController leftLeader_pidController;
	public CANPIDController rightLeader_pidController;

	public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public Drivetrain() {
		leftFrontFollower.follow(leftLeader);
		leftRearFollower.follow(leftLeader);
		rightFrontFollower.follow(rightLeader);
		rightRearFollower.follow(rightLeader);

		leftLeader.setOpenLoopRampRate(0.25);
		rightLeader.setOpenLoopRampRate(0.25);
    
		leftLeader.setClosedLoopRampRate(0.25);
		rightLeader.setClosedLoopRampRate(0.25);
        
		leftLeader.setSmartCurrentLimit(40, 60);
		rightLeader.setSmartCurrentLimit(40, 60);

		leftLeader.setSecondaryCurrentLimit(90);
		rightLeader.setSecondaryCurrentLimit(90);

		leftLeader.setInverted(false);
		rightLeader.setInverted(true);
	  
	  	leftLeaderEncoder = leftLeader.getEncoder(EncoderType.kHallSensor, 4096);
		rightLeaderEncoder = rightLeader.getEncoder(EncoderType.kHallSensor, 4096);

		leftLeader_pidController = leftLeader.getPIDController(); 
		rightLeader_pidController = rightLeader.getPIDController();

		// PID coefficients
		kP = 0.0175; 
		kI = 0.000005;
		kD = .05; 
		kIz = 12; 
		kFF = 0; 
		kMaxOutput = 1; 
		kMinOutput = -1;
		
		// set PID coefficients
		leftLeader_pidController.setP(kP, 0);
		leftLeader_pidController.setI(kI, 0);
		leftLeader_pidController.setD(kD, 0);
		leftLeader_pidController.setIZone(kIz, 0);
		leftLeader_pidController.setFF(kFF, 0);
		leftLeader_pidController.setOutputRange(kMinOutput, kMaxOutput, 0);

		rightLeader_pidController.setP(kP, 0);
		rightLeader_pidController.setI(kI, 0);
		rightLeader_pidController.setD(kD, 0);
		rightLeader_pidController.setIZone(kIz, 0);
		rightLeader_pidController.setFF(kFF, 0);
		rightLeader_pidController.setOutputRange(kMinOutput, kMaxOutput, 0);
	}
        
	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());
	}

	public void drive(DriveSignal driveSignal) {
		this.setPower(driveSignal.getLeft(), driveSignal.getRight());
	}

	public void autoDrive(double leftRotations, double rightRotations){
		this.leftLeader_pidController.setReference(leftRotations, ControlType.kPosition, 0);
		this.rightLeader_pidController.setReference(rightRotations, ControlType.kPosition, 0);
	}

	public void smartDrive (double leftRotations, double rightRotations) {
		this.leftLeader_pidController.setReference(leftRotations, ControlType.kSmartMotion, 1);
		this.rightLeader_pidController.setReference(rightRotations, ControlType.kSmartMotion, 1);
	}

	public void setPower(double leftSpeed, double rightSpeed) {
		leftLeader.set(leftSpeed);
		rightLeader.set(rightSpeed);
	}

	public void stopDrivetrain() {
		leftLeader.set(0);
		rightLeader.set(0);
	}

	public void enableBrakeMode() {
		leftLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
		rightLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public void disableBrakeMode() {
		leftLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
		rightLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
	}

	/*
	public double getLeftDistance() {
		return getLeftTicks() * Constants.DriveTrain.LEFT_DISTANCE_PER_PULSE;
	}

	public double getRightDistance() {
		return getRightTicks() * Constants.DriveTrain.RIGHT_DISTANCE_PER_PULSE;
	}
	*/

	public double getLeftTicks() {
		return leftLeaderEncoder.getPosition();
	}

	public double getRightTicks() {
		return rightLeaderEncoder.getPosition();
	}

	public double getLeftSpeed() {
        return leftLeaderEncoder.getVelocity();
    }

    public double getRightSpeed() {
        return rightLeaderEncoder.getVelocity();
	}
	
	public double getSpeed() {
        return (getLeftSpeed() + getRightSpeed()) / 2;
    }

	public double getDistance() {
		return (getLeftTicks() + getRightTicks()) / 2;
	}

	public double getLeftPower() {
		return leftLeader.get();
	}

	public double getRightPower() {
		return rightLeader.get();
	}

	public double getAngle() {
		double[] ypr = new double[3];
		pigeon.getYawPitchRoll(ypr);
		return ypr[0]; 
  	}

  	public double getPitch() {
		double[] ypr = new double[3];
		 pigeon.getYawPitchRoll(ypr);
		return ypr[1]; 
  	}

  	public double getRoll() {
		double[] ypr = new double[3];
		 pigeon.getYawPitchRoll(ypr);
		return ypr[2]; 
	  }

	public void resetPigeon() {
		pigeon.setYaw(0, 30);
	}

  	public void resetDriveEncoders() {
		leftLeaderEncoder.setPosition(0);
		rightLeaderEncoder.setPosition(0);
	}

	public boolean isHighGear() {
        return isHighGear;
    }

    public void setIsHighGear(boolean isHighGear) {
        this.isHighGear = isHighGear;
    }

	public void updateDashboard() {
		SmartDashboard.putNumber("Left Leader Current", leftLeader.getOutputCurrent());
		SmartDashboard.putNumber("Velocity", getSpeed());
		SmartDashboard.putNumber("Left Front  Current", leftFrontFollower.getOutputCurrent());
		SmartDashboard.putNumber("Left Rear  Current", leftRearFollower.getOutputCurrent());
		SmartDashboard.putNumber("Right Leader Current", rightLeader.getOutputCurrent());
		SmartDashboard.putNumber("Right Front  Current", rightFrontFollower.getOutputCurrent());
		SmartDashboard.putNumber("Right Rear  Current", rightRearFollower.getOutputCurrent());
		SmartDashboard.putNumber("Neo Ticks Left", getLeftTicks());
		SmartDashboard.putNumber("Neo Ticks Right", getRightTicks());
		SmartDashboard.putNumber("Angle", getAngle());
		//SmartDashboard.putNumber("Neo/Distance/Left", getLeftDistance());
		//SmartDashboard.putNumber("Neo/Distance/Right", getRightDistance());
		//SmartDashboard.putNumber("Neo/Distance/Total", getDistance());
	}
}
