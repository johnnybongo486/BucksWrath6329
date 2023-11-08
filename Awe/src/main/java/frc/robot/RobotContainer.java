package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ChuckItCommandGroup;
import frc.robot.commands.HighShotCommandGroup;
import frc.robot.commands.IntakeCubeCommandGroup;
import frc.robot.commands.LowShotCommandGroup;
import frc.robot.commands.MidShotCommandGroup;
import frc.robot.commands.ShootCommandGroup;
import frc.robot.commands.StopShooterCommandGroup;
import frc.robot.commands.StoreIntakeCommandGroup;
import frc.robot.commands.Drivetrain.PIDTurnToAngle;
import frc.robot.commands.Drivetrain.TeleopSwerve;
import frc.robot.commands.Intake.ReverseIntake;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.commands.Shooter.JoystickShooter;
import frc.robot.commands.Wrist.GoToStoreWristPosition;
import frc.robot.commands.Wrist.JoystickWrist;
import frc.robot.subsystems.*;
import frc.robot.auto.AutonomousSelector;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Autonomous Selector */
    private final AutonomousSelector autonomousSelector = new AutonomousSelector();
    
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick operator = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Setting Bot to Field Centric */
    private final Boolean robotCentric = false;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kBack.value);
    private final JoystickButton intakeButton = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton scoreButton = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton faceLeftButton = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton faceRightButton = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton faceRearButton = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton faceFrontButton = new JoystickButton(driver, XboxController.Button.kY.value);

    /* Operator Buttons */
    private final JoystickButton lowLevelButton = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton midLevelButton = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton highLevelButton = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton chuckCubeButton = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton spitCubeButton = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton stopShooterButton = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private final Swerve swerve = new Swerve();
    public static Wrist wrist = new Wrist();
    public static Intake intake = new Intake();
    public static LowerShooter lowerShooter = new LowerShooter();
    public static UpperShooter upperShooter = new UpperShooter();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        swerve.setDefaultCommand(
            new TeleopSwerve(
                swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                robotCentric
            )
        );

        //wrist.setDefaultCommand(new JoystickWrist());
        lowerShooter.setDefaultCommand(new JoystickShooter());
        upperShooter.setDefaultCommand(new JoystickShooter());
        wrist.setDefaultCommand(new GoToStoreWristPosition()); // does this help the broken loops?

        /* Configure the button bindings */
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

        faceLeftButton.whileTrue(new PIDTurnToAngle(
            swerve, 
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis), 
            () -> -driver.getRawAxis(rotationAxis), 
            robotCentric,
            90)); // was 270

        faceRightButton.whileTrue(new PIDTurnToAngle(
            swerve, 
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis), 
            () -> -driver.getRawAxis(rotationAxis), 
            robotCentric,
            270)); // was 90

        faceFrontButton.whileTrue(new PIDTurnToAngle(
            swerve, 
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis), 
            () -> -driver.getRawAxis(rotationAxis), 
            robotCentric,
            360));  // was 180
                
        faceRearButton.whileTrue(new PIDTurnToAngle(
            swerve, 
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis), 
            () -> -driver.getRawAxis(rotationAxis), 
            robotCentric,
            180)); // was 360

        intakeButton.whileTrue(new IntakeCubeCommandGroup());
        intakeButton.whileFalse(new StoreIntakeCommandGroup());

        scoreButton.onTrue(new ShootCommandGroup());



        /* Operator Buttons */
        lowLevelButton.onTrue(new LowShotCommandGroup());

        midLevelButton.onTrue(new MidShotCommandGroup());

        highLevelButton.onTrue(new HighShotCommandGroup());

        chuckCubeButton.onTrue(new ChuckItCommandGroup());   
        
        spitCubeButton.whileTrue(new ReverseIntake());
        spitCubeButton.whileFalse(new StopIntake());

        stopShooterButton.onTrue(new StopShooterCommandGroup());
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
    
    /* Passes Along Joystick Values for Elevator and Wrist */
    public double getOperatorLeftStickY() {
        return stickDeadband(this.operator.getRawAxis(1), 0.05, 0.0);
    }
 
    public double getOperatorRightStickY() {
        return stickDeadband(this.operator.getRawAxis(5), 0.05, 0.0);
    }

    /* Runs the Autonomous Selector*/
    public Command getAutonomousCommand() {
        return autonomousSelector.getCommand(swerve);
    }
}
