package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
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

  public static Drivetrain drivetrain = new Drivetrain();
  public Joystick Driver;

  public static final  long RUMBLE_MILLIS = 250;
  public static final double RUMBLE_INTENSITY = 1.0;
  
  public RobotContainer() {
    Driver = new Joystick(0);
    configureButtonBindings();
    CommandScheduler.getInstance().setDefaultCommand(RobotContainer.drivetrain, new JoystickDrive());

  }

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
     Driver.setRumble(GenericHID.RumbleType.kLeftRumble, RobotContainer.RUMBLE_INTENSITY);
 }

 public void rumbleRight() {
     Driver.setRumble(GenericHID.RumbleType.kRightRumble, RobotContainer.RUMBLE_INTENSITY);
 }

 public void stopRumble() {
     Driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
     Driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
 }
}