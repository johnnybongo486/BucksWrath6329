package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.AxisCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static RobotContainer robotContainer;
  private Command m_autonomousCommand;
  public static AxisCamera limelight;
  public static UsbCamera intakeCamera;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    
    //limelight = CameraServer.addAxisCamera("limelight", "10.63.29.11");
    //RobotContainer.limelight.cameraMode();
    //NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(2);
    //limelight.setFPS(10);
    //limelight.setResolution(160,120);

    intakeCamera = CameraServer.startAutomaticCapture("Intake", 0);
    intakeCamera.setResolution(160,120);
    intakeCamera.setFPS(30);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    RobotContainer.drivetrain.updateDashboard();
    RobotContainer.shooter.updateDashboard();
    RobotContainer.leftClimber.updateDashboard();
    RobotContainer.rightClimber.updateDashboard();
    RobotContainer.limelight.updateDashboard();
    RobotContainer.upperShooter.updateDashboard();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    RobotContainer.drivetrain.resetPigeon();
    RobotContainer.drivetrain.setNeutralMode(NeutralMode.Brake);
    RobotContainer.drivetrain.resetDriveEncoders(); 
    RobotContainer.leftClimber.resetClimberEncoder();
    RobotContainer.rightClimber.resetClimberEncoder();
    RobotContainer.leftClimber.zeroTarget();
    RobotContainer.rightClimber.zeroTarget();
    
    m_autonomousCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();

      RobotContainer.drivetrain.setNeutralMode(NeutralMode.Brake);
    }
    // Comment out for matches
    RobotContainer.leftClimber.resetClimberEncoder();
    RobotContainer.rightClimber.resetClimberEncoder();
    RobotContainer.leftClimber.zeroTarget();
    RobotContainer.rightClimber.zeroTarget();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    RobotContainer.drivetrain.setNeutralMode(NeutralMode.Coast);
    
    // Use for matches
    RobotContainer.limelight.cameraMode();

    // Use for calibrating vision
    //RobotContainer.limelight.visionMode();
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
