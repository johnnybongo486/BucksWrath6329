package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.Climber.ClimberDeploy;
import frc.robot.Commands.Climber.JoystickClimber;
import frc.robot.Commands.Drivetrain.CompressorCommand;
import frc.robot.Commands.Drivetrain.HighGear;
import frc.robot.Commands.Drivetrain.JoystickDrive;
import frc.robot.Commands.Drivetrain.LowGear;
import frc.robot.Commands.Intake.IntakeRetract;
import frc.robot.Commands.Intake.ReverseIntake;
import frc.robot.Commands.Intake.RunIntake;
import frc.robot.Commands.Intake.StopIntake;
import frc.robot.Commands.Serializer.StopSerializer;
import frc.robot.Commands.Shooter.JoystickShooter;
import frc.robot.Commands.Shooter.StoreShooterPiston;
import frc.robot.Subsystems.AirCompressor;
import frc.robot.Subsystems.ClimberPiston;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.IntakePiston;
import frc.robot.Subsystems.LeftClimber;
import frc.robot.Subsystems.RightClimber;
import frc.robot.Subsystems.Serializer;
import frc.robot.Subsystems.Shifter;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.ShooterPiston;

public class RobotContainer {

    public static Drivetrain drivetrain = new Drivetrain();
    public static Shifter shifter = new Shifter();
    public static Intake intake = new Intake();
    public static Serializer serializer = new Serializer();
    public static Shooter shooter = new Shooter();
    public static ShooterPiston shooterPiston = new ShooterPiston();
    public static LeftClimber leftClimber = new LeftClimber();
    public static RightClimber rightClimber = new RightClimber();
    public static IntakePiston intakePiston = new IntakePiston();
    public static ClimberPiston climberPiston = new ClimberPiston();
    public static AirCompressor airCompressor = new AirCompressor();

    // Joysticks
    public Joystick Driver;
    public Joystick Operator;

    public static final  long RUMBLE_MILLIS = 250;
    public static final double RUMBLE_INTENSITY = 1.0;

    public JoystickButton lowGearButton;
    public JoystickButton highGearButton;
    public JoystickButton intakeButton;
    public JoystickButton reverseIntakeButton;
  
    public RobotContainer() {
        Driver = new Joystick(0);
        Operator = new Joystick(1);

        configureButtonBindings();
        
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.drivetrain, new JoystickDrive());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.shifter, new LowGear());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.intake, new StopIntake());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.intakePiston, new IntakeRetract());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.serializer, new StopSerializer());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.shooter, new JoystickShooter());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.shooterPiston, new StoreShooterPiston());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.leftClimber, new JoystickClimber());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.rightClimber, new JoystickClimber());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.climberPiston, new ClimberDeploy());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.airCompressor, new CompressorCommand());
    }

    private void configureButtonBindings() {
        lowGearButton = new JoystickButton(Driver, 1);
        lowGearButton.whenPressed(new LowGear());

        highGearButton = new JoystickButton(Driver, 2);
        highGearButton.whenPressed(new HighGear());

        intakeButton = new JoystickButton(Driver, 5);
        intakeButton.whileHeld(new ReverseIntake());
        intakeButton.whenReleased(new StopIntake());

        reverseIntakeButton = new JoystickButton(Driver, 6);
        reverseIntakeButton.whileHeld(new RunIntake());
        reverseIntakeButton.whenReleased(new StopIntake());
    } 

    public Joystick getDriver() {
        return Driver;
    }

    public Joystick getOperator() {
        return Operator;
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

    public double getOperatorLeftStickY() {
        return stickDeadband(this.Operator.getRawAxis(1), 0.05, 0.0);
    }
 
    public double getOperatorRightStickY() {
        return stickDeadband(this.Operator.getRawAxis(3), 0.05, 0.0);
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