/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
/*
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
*/

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.Drivetrain.TurnNinetyDegrees;
import frc.robot.Auto.Shooter.Center6BallAuto;
import frc.robot.Auto.Shooter.CenterAuto;
import frc.robot.Auto.Shooter.DriveThreeFeet;
import frc.robot.Auto.Shooter.Left5BallAuto;
import frc.robot.Auto.Shooter.LeftAuto;
import frc.robot.Auto.Shooter.Right5BallAuto;
import frc.robot.Auto.Shooter.RightAuto;
import frc.robot.Subsystems.*;

public class Robot extends TimedRobot {
  // private final I2C.Port i2cPort = I2C.Port.kOnboard;
  // private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  Command autonomousCommand;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String kDriveThreeFeet = "Drive 3 Feet";
  private static final String kTurnNinetyDegrees = "TurnNinetyDegrees";
  private static final String kCenter6BallAuto = "Center Six Ball Auto";
  private static final String kLeftAuto = "Left Auto";
  private static final String kRight5BallAuto = "Right 5 Ball Auto";
  private static final String kLeft5BallAuto = "Left 5 Ball Auto";
  private static final String kRightAuto = "Right Auto";
  private static final String kCenterAuto = "Center Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  public static Robot robot;
  public static Shooter Shooter;
  public static Drivetrain Drivetrain;
  public static AirCompressor AirCompressor;
  public static IntakePiston IntakePiston;
  public static ShooterPiston ShooterPiston;
  public static Serializer Serializer;
  public static AxisCamera limelight;
  public static LeftElevator LeftElevator;
  public static RightElevator RightElevator;
  public static Intake Intake;
  public static Shifter Shifter;
  public static WOF WOF;

  // private final ColorMatch m_ColorMatcher = new ColorMatch();
  // private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  // private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  // private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  // private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  public static OI oi;

  @Override
  public void robotInit() {
    robot = this;
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    m_chooser.addOption("Drive 3 feet", kDriveThreeFeet);
    m_chooser.addOption("Turn 90 Degrees", kTurnNinetyDegrees);
    m_chooser.addOption("Center Six Ball Auto", kCenter6BallAuto);
    m_chooser.addOption("Left Auto", kLeftAuto);
    m_chooser.addOption("Right 5 Ball Auto", kRight5BallAuto);
    m_chooser.addOption("Left 5 Ball Auto", kLeft5BallAuto);
    m_chooser.addOption("Right Auto", kRightAuto);
    m_chooser.addOption("Center Auto", kCenterAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    
    Shooter = new Shooter();
    Drivetrain = new Drivetrain();
    AirCompressor = new AirCompressor();
    LeftElevator = new LeftElevator();
    RightElevator = new RightElevator();
    Intake = new Intake();
    IntakePiston = new IntakePiston();
    ShooterPiston = new ShooterPiston();
    Serializer = new Serializer();
    Shifter = new Shifter();
    WOF = new WOF();

    // Turn on Limelight Stream
    limelight = CameraServer.getInstance().addAxisCamera("limelight", "10.63.29.11");
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    limelight.setFPS(50);
    limelight.setResolution(160,120);

    /*
    m_ColorMatcher.addColorMatch(kBlueTarget);
    m_ColorMatcher.addColorMatch(kGreenTarget);
    m_ColorMatcher.addColorMatch(kRedTarget);
    m_ColorMatcher.addColorMatch(kYellowTarget);
    */

    // Always Last
    oi = new OI();
  }

  @Override
  public void robotPeriodic() {
    Drivetrain.updateDashboard();
    Shooter.updateDashboard();
    LeftElevator.updateDashboard();
    RightElevator.updateDashboard();
    Serializer.updateDashboard();
    WOF.updateDashboard();

    /*
    Color detectedColor = m_colorSensor.getColor();

    String colorString;
    ColorMatchResult match = m_ColorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget){
      colorString = "Blue";
    } else if (match.color == kRedTarget){
      colorString = "Red";
    }else if (match.color == kGreenTarget){
      colorString = "Green";
    }else if (match.color == kYellowTarget){
      colorString = "Yellow";
    }else {
      colorString = "Unknown";
    }

    double IR = m_colorSensor.getIR();

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);

    int proximity = m_colorSensor.getProximity();

    SmartDashboard.putNumber("Proximity", proximity);
    */

  }

  @Override
	public void disabledInit() {
    Drivetrain.resetPigeon();
    Drivetrain.resetDriveEncoders();
    Drivetrain.disableBrakeMode();
  }

  @Override
  public void disabledPeriodic() {
    // Use for matches
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

    // Use for calibrating vision
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
  }

  @Override
  public void autonomousInit() {
    Drivetrain.resetPigeon();
    Drivetrain.enableBrakeMode();
    LeftElevator.resetElevatorEncoder();
    RightElevator.resetElevatorEncoder();
    WOF.enableBrakeMode();
    Drivetrain.resetDriveEncoders();
    Shooter.resetShooterEncoder();

    m_autoSelected = m_chooser.getSelected();

    System.out.println("Auto selected: " + m_autoSelected);

    switch (m_autoSelected) {
      case kDriveThreeFeet:
        autonomousCommand = new DriveThreeFeet();
        break;
      
      case kTurnNinetyDegrees:
        autonomousCommand = new TurnNinetyDegrees();
        break;
      
      case kCenterAuto:
        autonomousCommand = new CenterAuto();
        break;

      case kLeftAuto:
        autonomousCommand = new LeftAuto();
        break;

      case kRight5BallAuto:
        autonomousCommand = new Right5BallAuto();
        break;

      case kLeft5BallAuto:
        autonomousCommand = new Left5BallAuto();
        break;

      case kCenter6BallAuto:
        autonomousCommand = new Center6BallAuto();
        break;

      case kRightAuto:
        autonomousCommand = new RightAuto();
        break;

      case kDefaultAuto:
        default:
        autonomousCommand = new DriveThreeFeet();
        break;
       
    }

    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null){
      autonomousCommand.cancel();
    }

    LeftElevator.resetElevatorEncoder();  // FOR TESTING ONLY
    RightElevator.resetElevatorEncoder(); // FOR TESTING ONLY
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }
}
