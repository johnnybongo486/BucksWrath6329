package frc.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.*;

public class Robot extends TimedRobot {
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
  public static AxisCamera limelight;

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
    Shooter.updateDashboard();
  }

  @Override
	public void disabledInit() {
    Shooter.resetShooterEncoder();
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
    Shooter.resetShooterEncoder();

    m_autoSelected = m_chooser.getSelected();

    System.out.println("Auto selected: " + m_autoSelected);

    switch (m_autoSelected) {
      case kDriveThreeFeet:
        // autonomousCommand = new DriveThreeFeet();
        break;
      
      case kTurnNinetyDegrees:
        // autonomousCommand = new TurnNinetyDegrees();
        break;
      
      case kCenterAuto:
        // autonomousCommand = new CenterAuto();
        break;

      case kLeftAuto:
        // autonomousCommand = new LeftAuto();
        break;

      case kRight5BallAuto:
        // autonomousCommand = new Right5BallAuto();
        break;

      case kLeft5BallAuto:
        // autonomousCommand = new Left5BallAuto();
        break;

      case kCenter6BallAuto:
        // autonomousCommand = new Center6BallAuto();
        break;

      case kRightAuto:
        // autonomousCommand = new RightAuto();
        break;

      case kDefaultAuto:
        default:
        // autonomousCommand = new DriveThreeFeet();
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