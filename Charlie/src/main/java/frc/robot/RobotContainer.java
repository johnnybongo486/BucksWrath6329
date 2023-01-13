package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.Autonomous.*;
import frc.robot.Commands.*;
import frc.robot.Subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton visionAlignButton = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton otherVisionAlignButton = new JoystickButton(driver, XboxController.Button.kB.value);


    /* Subsystems */
    public static Swerve s_Swerve = new Swerve();


    // Commands

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
      boolean fieldRelative = true;
      boolean openLoop = true;
      s_Swerve.setDefaultCommand(new TeleopSwerve(s_Swerve, driver, translationAxis, strafeAxis, rotationAxis, fieldRelative, openLoop));

      // Configure the button bindings
      configureButtonBindings();
    }

    /**
    * Use this method to define your button->command mappings. Buttons can be created by
    * instantiating a {@link GenericHID} or one of its subclasses ({@link
    * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
    * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
    */
    private void configureButtonBindings() {
      /* Driver Buttons */
      zeroGyro.toggleOnTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));      
    }

  public Swerve getSwerveSubsystem() {
    return s_Swerve;
  }

  public static double stickDeadband(double value, double deadband, double center) {
    return (value < (center + deadband) && value > (center - deadband)) ? center : value;
  }

  public double getDriverLeftStickY() {
    return stickDeadband(driver.getRawAxis(translationAxis), 0.01, 0.0);
  }

  public double getDriverLeftStickX() {
    return stickDeadband(driver.getRawAxis(strafeAxis), 0.01, 0.0);
  }

  public double getDriverRightStickX() {
    return stickDeadband(driver.getRawAxis(rotationAxis), 0.05, 0.0);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new ExampleAuto(s_Swerve);
  }

}
