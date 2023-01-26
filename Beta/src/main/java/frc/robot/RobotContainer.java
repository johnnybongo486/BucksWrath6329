package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.auto.*;
import frc.robot.commands.Drivetrain.TeleopSwerve;
import frc.robot.commands.Elevator.ElevatorIn;
import frc.robot.commands.Elevator.ElevatorOut;
import frc.robot.commands.Elevator.GoToHighPosition;
import frc.robot.commands.Elevator.GoToMidPosition;
import frc.robot.commands.Elevator.JoystickElevator;
import frc.robot.commands.Intake.IntakeObject;
import frc.robot.commands.Intake.ScoreObject;
import frc.robot.commands.Intake.StoreObject;
import frc.robot.commands.LEDs.SetConeMode;
import frc.robot.commands.LEDs.SetCubeMode;
import frc.robot.commands.Wrist.JoystickWrist;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick operator = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton intakeButton = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton scoreButton = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

    /* Operator Buttons */
    private final JoystickButton coneModeButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    private final JoystickButton cubeModeButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton elevatorVerticalButton = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton elevatorAngledButton = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton midLevelButton = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton highLevelButton = new JoystickButton(operator, XboxController.Button.kY.value);

    /* Subsystems */
    private final Swerve swerve = new Swerve();
    public static Elevator elevator = new Elevator();
    public static Wrist wrist = new Wrist();
    public static CANdleSubsystem candleSubsystem = new CANdleSubsystem();
    public static Intake intake = new Intake();
    public static AirCompressor airCompressor = new AirCompressor();
    public static ElevatorPiston elevatorPiston = new ElevatorPiston();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        swerve.setDefaultCommand(
            new TeleopSwerve(
                swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        elevator.setDefaultCommand(new JoystickElevator());
        wrist.setDefaultCommand(new JoystickWrist());
        airCompressor.setDefaultCommand(new CompressorCommand());

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
        zeroGyro.onTrue(new InstantCommand(() -> swerve.zeroGyro()));
        coneModeButton.onTrue(new SetConeMode());
        cubeModeButton.onTrue(new SetCubeMode());

        intakeButton.whileTrue(new IntakeObject());
        intakeButton.whileFalse(new StoreObject());
        
        scoreButton.whileTrue(new ScoreObject());
        scoreButton.whileFalse(new StoreObject());

        midLevelButton.onTrue(new GoToMidPosition());
        highLevelButton.onTrue(new GoToHighPosition());

        elevatorVerticalButton.onTrue(new ElevatorIn());
        elevatorAngledButton.onTrue(new ElevatorOut());
    }

    public Joystick getDriver() {
        return driver;
    }

    public Joystick getOperator() {
        return operator;
    }

    public static double stickDeadband(double value, double deadband, double center) {
        return (value < (center + deadband) && value > (center - deadband)) ? center : value;
    }

    public double getOperatorLeftStickY() {
        return stickDeadband(this.operator.getRawAxis(1), 0.05, 0.0);
    }
 
    public double getOperatorRightStickY() {
        return stickDeadband(this.operator.getRawAxis(5), 0.05, 0.0);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new ExampleAuto(swerve);
    }
}