package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.JoystickShooter;
import frc.robot.commands.StopSerializer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.UpperShooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static Drivetrain drivetrain = new Drivetrain();
  public static Serializer serializer = new Serializer();
  public static Shooter shooter = new Shooter();
  public static UpperShooter upperShooter = new UpperShooter();

//Establish Controllers
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);

// Drive Controls
  private final int throttle = XboxController.Axis.kLeftY.value;
  private final int turn = XboxController.Axis.kRightX.value;

// Driver Buttons
  private final JoystickButton lowShotButton = new JoystickButton(operator, XboxController.Button.kA.value);
  private final JoystickButton highShotButton = new JoystickButton(operator, XboxController.Button.kB.value);
  private final JoystickButton shootButton = new JoystickButton(driver, XboxController.Button.kRightBumper.value);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drivetrain.setDefaultCommand(new JoystickDrive());
    shooter.setDefaultCommand(new JoystickShooter());
    upperShooter.setDefaultCommand(new JoystickShooter());
    serializer.setDefaultCommand(new StopSerializer());

    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
  

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.

  }

  /* Public access to joystick values */
  public Joystick getDriver() {
    return driver;
  }

  public Joystick getOperator() {
    return operator;
  }

  /* Sets Joystick Deadband */
  public static double stickDeadband(double value, double deadband, double center) {
    return (value < (center + deadband) && value > (center - deadband)) ? center : value;
  }

  /* Passes Along Joystick Values for Upper and Lower Shooter*/
  public double getOperatorLeftStickY() {
    return stickDeadband(this.operator.getRawAxis(1), 0.05, 0.0);
  }

  public double getOperatorRightStickY() {
    return stickDeadband(this.operator.getRawAxis(5), 0.05, 0.0);
  }

  public double getDriverLeftStickY() {
    return stickDeadband(this.driver.getRawAxis(1), 0.05, 0.0);
  }

  /* Passes Along Joystick Values for Drivetrain*/
  public double getDriverRightStickX() {
    return stickDeadband(this.driver.getRawAxis(4), 0.05, 0.0);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(drivetrain);
  }
}
