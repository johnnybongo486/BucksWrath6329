package frc.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.AirCompressor;
import frc.robot.Subsystems.Claw;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Shifter;
import frc.robot.Subsystems.SlideDrive;
import frc.robot.Subsystems.Wrist;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public static OI oi;
  public static Robot robot;
  public static Drivetrain Drivetrain;
  public static SlideDrive SlideDrive;
  public static Wrist Wrist;
  public static Shifter Shifter;
  public static Claw Claw;
  public static Intake Intake;
  public static AirCompressor AirCompressor;
  public static AxisCamera limelight;

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    robot = this;

    RobotMap.init();

    Drivetrain = new Drivetrain();
    SlideDrive = new SlideDrive();
    AirCompressor = new AirCompressor();
    Shifter = new Shifter();
    Wrist = new Wrist();
    Intake = new Intake();
    Claw = new Claw();

    AxisCamera limelight = CameraServer.getInstance().addAxisCamera("limelight", "10.99.99.11");
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    limelight.setFPS(50);
    limelight.setResolution(160,120);

    // ALWAYS LAST!
    oi = new OI();
  }

  @Override
  public void robotPeriodic() {
    Robot.Drivetrain.updateDashboard();
    Robot.SlideDrive.updateDashboard();
    Robot.Wrist.updateDashboard();
  }

  @Override
  public void disabledPeriodic() {
    
    // Use for matches
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

    // Use for calibrating vision
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);

  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    Wrist.resetWristEncoder();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    /*
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
    */
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    Robot.Drivetrain.updateDashboard();
    Robot.SlideDrive.updateDashboard();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
