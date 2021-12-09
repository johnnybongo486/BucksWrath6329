package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.JoystickDrive;
import frc.robot.Subsystems.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  public Joystick Driver= new Joystick(0);

  public static final  long RUMBLE_MILLIS = 250;
  public static final double RUMBLE_INTENSITY = 1.0;
  // The robot's subsystems and commands are defined here...

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    CommandScheduler.getInstance().setDefaultCommand(drivetrain, new JoystickDrive(drivetrain, getDriverLeftStickY(), getDriverRightStickX()));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }

  public Joystick getDriver() {
    return Driver;
  }

  public static double stickDeadband(double value, double deadband, double center) {
    return (value < (center + deadband) && value > (center - deadband)) ? center : value;
  }

  public double getDriverLeftStickY() {
    return stickDeadband(this.Driver.getRawAxis(1), 0.05, 0.0);
  }

  public double getDriverRightStickX() {
    return stickDeadband(this.Driver.getRawAxis(4), 0.05, 0.0);
  }

  public void rumbleLeft() {
    Driver.setRumble(GenericHID.RumbleType.kLeftRumble, RUMBLE_INTENSITY);
  }

public void rumbleRight() {
    Driver.setRumble(GenericHID.RumbleType.kRightRumble, RUMBLE_INTENSITY);
}

public void stopRumble() {
    Driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    Driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
}

public Drivetrain getDrivetrainSubsystem() {
  return drivetrain;
}
}