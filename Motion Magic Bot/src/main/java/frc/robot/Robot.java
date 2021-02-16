package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Commands.Auto.BallFinderAuto;
import frc.robot.Commands.Auto.DriveThreeFeet;
import frc.robot.Commands.Auto.Turn90Degrees;
import frc.robot.Commands.Auto.DPDriveThreeFeet;
import frc.robot.Subsystems.*;

public class Robot extends TimedRobot {
  Command autonomousCommand;
  private static final String kDefaultAuto = "Default";
  private static final String kBallFinderAuto = "Ball Finder Auto";
  private static final String kDriveThreeFeet = "Drive 3 Feet";
  private static final String kDPDriveThreeFeet = "DP Drive 3 Feet";
  private static final String kTurnNinetyDegrees = "TurnNinetyDegrees";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  public static Robot robot;
  public static Drivetrain Drivetrain;
  public static SlideDrive SlideDrive;
  public static AxisCamera limelight;
  public static AirCompressor AirCompressor;

  public static OI oi;

  @Override
  public void robotInit() {
    robot = this;
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Ball Finder Auto", kBallFinderAuto);
    m_chooser.addOption("Drive 3 feet", kDriveThreeFeet);
    m_chooser.addOption("DP Drive 3 feet", kDPDriveThreeFeet);
    m_chooser.addOption("Turn 90 Degrees", kTurnNinetyDegrees);

    SmartDashboard.putData("Auto choices", m_chooser);
    
    Drivetrain = new Drivetrain();
    SlideDrive = new SlideDrive();
    AirCompressor = new AirCompressor();

    // Turn on Limelight Stream
    limelight = CameraServer.getInstance().addAxisCamera("limelight", "10.63.29.11");
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    limelight.setFPS(50);
    limelight.setResolution(160,120);

    // Always Last
    oi = new OI();
  }

  @Override
  public void robotPeriodic() {
    Drivetrain.updateDashboard();
    SlideDrive.updateDashboard();
  }

  @Override
	public void disabledInit() {
    Drivetrain.resetPigeon();
    Drivetrain.resetDriveEncoders();
    Drivetrain.setNeutralMode(NeutralMode.Coast);
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
    Drivetrain.setNeutralMode(NeutralMode.Brake);
    Drivetrain.resetDriveEncoders();

    m_autoSelected = m_chooser.getSelected();

    System.out.println("Auto selected: " + m_autoSelected);

    switch (m_autoSelected) {
      case kDriveThreeFeet:
        autonomousCommand = new DriveThreeFeet();
        break;

      case kDPDriveThreeFeet:
        autonomousCommand = new DPDriveThreeFeet();
        break;
      
      case kTurnNinetyDegrees:
        autonomousCommand = new Turn90Degrees();
        break;
      
      case kBallFinderAuto:
        autonomousCommand = new BallFinderAuto();
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