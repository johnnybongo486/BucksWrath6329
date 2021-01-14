package frc.robot.Subsystems;

import frc.robot.Models.*;
import frc.robot.Commands.JoystickDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.ExternalFollower;

public class Drivetrain extends Subsystem {

    private boolean isHighGear = false;

    public TalonSRX leftLead = new TalonSRX(6);
    public TalonSRX rightLead = new TalonSRX(7);
    public TalonSRX spareTalon = new TalonSRX(0);

    public PigeonIMU pigeon = new PigeonIMU(spareTalon);

    public CANSparkMax leftRearFollower = new CANSparkMax(1, MotorType.kBrushless);
    public CANSparkMax leftFrontFollower = new CANSparkMax(2, MotorType.kBrushless);
    public CANSparkMax rightRearFollower = new CANSparkMax(3, MotorType.kBrushless);
    public CANSparkMax rightFrontFollower = new CANSparkMax(4, MotorType.kBrushless);

    public CANEncoder leftEncoder = new CANEncoder(leftFrontFollower);
    public CANEncoder rightEncoder = new CANEncoder(rightFrontFollower);

    public double _leftOffset;
    public double _rightOffset;

    public void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    public Drivetrain() {
        //  Sparks follow SRX
        leftRearFollower.follow(ExternalFollower.kFollowerPhoenix, 6);
        leftFrontFollower.follow(ExternalFollower.kFollowerPhoenix, 6);
        rightRearFollower.follow(ExternalFollower.kFollowerPhoenix, 7);
        rightFrontFollower.follow(ExternalFollower.kFollowerPhoenix, 7);

        //  Set Motor Direction
        leftLead.setInverted(true);
        leftRearFollower.setInverted(true);
        leftFrontFollower.setInverted(true);

        rightLead.setInverted(false);
        rightRearFollower.setInverted(false);
        rightFrontFollower.setInverted(false);

        // Set Sensors
        // leftLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0 , 30);
        // rightLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0 , 30);
        // leftLead.setSensorPhase(true);
        // rightLead.setSensorPhase(true);

        leftEncoder = leftFrontFollower.getEncoder();
        rightEncoder = rightFrontFollower.getEncoder();

        // ESC Settings

        resetPigeon();
        setNeutralMode(NeutralMode.Brake);

        leftLead.enableCurrentLimit(true);
        rightLead.enableCurrentLimit(true);

        leftLead.configContinuousCurrentLimit(60);
        leftLead.configPeakCurrentLimit(100);
        leftRearFollower.setSmartCurrentLimit(40, 60);
        leftFrontFollower.setSmartCurrentLimit(40, 60);

        rightLead.configContinuousCurrentLimit(60);
        rightLead.configPeakCurrentLimit(100);
        rightRearFollower.setSmartCurrentLimit(40, 60);
        rightFrontFollower.setSmartCurrentLimit(40, 60);

        leftLead.configOpenloopRamp(0.25);
        leftRearFollower.setOpenLoopRampRate(0.25);
        leftFrontFollower.setOpenLoopRampRate(0.25);

        rightLead.configOpenloopRamp(0.25);
        rightRearFollower.setOpenLoopRampRate(0.25);
        rightFrontFollower.setOpenLoopRampRate(0.25);
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

    public void setNeutralMode(NeutralMode neutralMode) {
        this.leftLead.setNeutralMode(neutralMode);
        this.rightLead.setNeutralMode(neutralMode);
    }

    public boolean isHighGear() {
        return isHighGear;
    }

    public void setIsHighGear(boolean isHighGear) {
        this.isHighGear = isHighGear;
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

    public double getLeftSpeed() {
        return leftEncoder.getVelocity();
    }

    public double getRightSpeed()  {
        return rightEncoder.getVelocity();
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Drivetrain / Left Lead Current", leftLead.getOutputCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Rear Follower Current", leftRearFollower.getOutputCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Front Follower Current", leftFrontFollower.getOutputCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Lead Current", rightLead.getOutputCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Rear Follower Current", rightRearFollower.getOutputCurrent());
        SmartDashboard.putNumber("Drivetrain / Right Front Follower Current", rightFrontFollower.getOutputCurrent());
        SmartDashboard.putNumber("Drivetrain / Left Speed", getLeftSpeed());
        SmartDashboard.putNumber("Drivetrain / Right Speed", getRightSpeed());
        SmartDashboard.putNumber("Drivetrain / Current Angle", getAngle());
        SmartDashboard.putNumber("Drivetrain / Current Angular Rate", getRoll());
        SmartDashboard.putBoolean("Drivetrain / High Gear", isHighGear);

    }
}