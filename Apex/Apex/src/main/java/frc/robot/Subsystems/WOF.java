package frc.robot.Subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Commands.WOF.StopWOF;

public class WOF extends Subsystem {

  private CANSparkMax wofESC = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
  public CANEncoder wofEncoder = new CANEncoder(wofESC);
  public CANPIDController WOF_pidController;

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  public void initDefaultCommand() {
    setDefaultCommand(new StopWOF());
  }

  public WOF() {
    wofESC.setClosedLoopRampRate(0.25);
    wofESC.setSmartCurrentLimit(20, 30);
    wofESC.setSecondaryCurrentLimit(40);
    wofESC.setInverted(false);
    wofEncoder = wofESC.getEncoder(EncoderType.kHallSensor, 4096);
    WOF_pidController = wofESC.getPIDController();

    // PID coefficients
    kP = 0.1;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 1;
    kMinOutput = -1;

    // set PID coefficients
    WOF_pidController.setP(kP, 0);
    WOF_pidController.setI(kI, 0);
    WOF_pidController.setD(kD, 0);
    WOF_pidController.setIZone(kIz, 0);
    WOF_pidController.setFF(kFF, 0);
    WOF_pidController.setOutputRange(kMinOutput, kMaxOutput, 0);
  }

  public void autoRotate(double rotations) {
    this.WOF_pidController.setReference(rotations, ControlType.kPosition, 0);
  }

  public void stopWOF() {
    this.wofESC.set(0);
  }

  public void enableBrakeMode() {
    wofESC.setIdleMode(CANSparkMax.IdleMode.kBrake);
  }

  public void disableBrakeMode() {
    wofESC.setIdleMode(CANSparkMax.IdleMode.kCoast);
  }

  public double getWOFTicks() {
    return wofEncoder.getPosition();
  }

  public void resetWOFEncoders() {
    wofEncoder.setPosition(0);
  }

  public void updateDashboard() {
    SmartDashboard.putNumber("WOF Current", wofESC.getOutputCurrent());
    SmartDashboard.putNumber("WOF Ticks", getWOFTicks());
  }

}