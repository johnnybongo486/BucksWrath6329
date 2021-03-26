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
import frc.robot.Commands.Auto.AutoFindTarget;
import frc.robot.Commands.Auto.BallFinderAuto;
import frc.robot.Commands.Auto.DriveThreeFeet;
import frc.robot.Commands.Auto.Turn90Degrees;
import frc.robot.Subsystems.*;

public class Robot extends TimedRobot {
  Command autonomousCommand;
  private static final String kDefaultAuto = "Default";
  private static final String kBallFinderAuto = "Ball Finder Auto";
  private static final String kDriveThreeFeet = "Drive 3 Feet";
  private static final String kTurnNinetyDegrees = "TurnNinetyDegrees";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  public static Robot robot;
  public static Drivetrain Drivetrain;
  public static SlideDrive SlideDrive;
  public static AxisCamera shooterLimelight;
  public static AxisCamera intakeLimelight;
  public static Shooter Shooter;
  public static Intake Intake;
  public static IntakePiston IntakePiston;
  public static Serializer Serializer;
  public static ShooterPiston ShooterPiston;
  public static Hopper Hopper;
  public static AirCompressor AirCompressor;

  public static OI oi;

  @Override
  public void robotInit() {
    robot = this;
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Ball Finder Auto", kBallFinderAuto);
    m_chooser.addOption("Drive 3 feet", kDriveThreeFeet);
    m_chooser.addOption("Turn 90 Degrees", kTurnNinetyDegrees);

    SmartDashboard.putData("Auto choices", m_chooser);
    
    Drivetrain = new Drivetrain();
    SlideDrive = new SlideDrive();
    Shooter = new Shooter();
    Intake = new Intake();
    IntakePiston = new IntakePiston();
    Serializer = new Serializer();
    Hopper = new Hopper();
    ShooterPiston = new ShooterPiston();
    AirCompressor = new AirCompressor();

    // Turn on Limelight Stream
    shooterLimelight = CameraServer.getInstance().addAxisCamera("limelight-shooter", "10.63.29.11");
    NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("ledMode").setNumber(1);
    shooterLimelight.setFPS(50);
    shooterLimelight.setResolution(160,120);

    intakeLimelight = CameraServer.getInstance().addAxisCamera("limelight-intake", "10.63.29.12");
    NetworkTableInstance.getDefault().getTable("limelight-intake").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight-intake").getEntry("ledMode").setNumber(1);
    intakeLimelight.setFPS(50);
    intakeLimelight.setResolution(160,120);

    // Always Last
    oi = new OI();
  }

  @Override
  public void robotPeriodic() {
    Drivetrain.updateDashboard();
    SlideDrive.updateDashboard();
    Shooter.updateDashboard();
  }

  @Override
	public void disabledInit() {
    Drivetrain.resetPigeon();
    Drivetrain.resetDriveEncoders();
    Drivetrain.setNeutralMode(NeutralMode.Coast);

    // Shooter
    NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("camMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("ledMode").setNumber(3);

    // Intake
    NetworkTableInstance.getDefault().getTable("limelight-intake").getEntry("camMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight-intake").getEntry("ledMode").setNumber(1);
  }

  @Override
  public void disabledPeriodic() {
   
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