package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Autos.ExampleAuto;
import frc.robot.Commands.TeleopSwerve;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.PDH;
import frc.robot.Subsystems.Swerve;

public class RobotContainer {
  // Robot subsystems
  private Swerve m_swerve = new Swerve();
  public static PDH pdh = new PDH();
  public static Limelight limelight = new Limelight();

  // Xbox controllers
  private final CommandXboxController driver =
      new CommandXboxController(Constants.OperatorConstants.xboxController1Port);

  // Drive Controls
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  public RobotContainer() {
    configureButtonBindings();
    configureAxisActions();
  }

  /** Used for defining button actions. */
  private void configureButtonBindings() {

    /* Driver Buttons */
    driver.x().onTrue(new InstantCommand(() -> m_swerve.zeroGyro()));
  }

  /** Used for joystick/xbox axis actions. */
  private void configureAxisActions() {
    m_swerve.setDefaultCommand(
        new TeleopSwerve(
            m_swerve,
            () -> -driver.getRawAxis(translationAxis),
            () -> driver.getRawAxis(strafeAxis),
            () -> -driver.getRawAxis(rotationAxis),
            () -> driver.rightStick().getAsBoolean()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new ExampleAuto(m_swerve);
  }
}