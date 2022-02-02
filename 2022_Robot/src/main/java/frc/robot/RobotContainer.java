package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.Intake.StopCenterIntake;
import frc.robot.Commands.Auto.AutonomousSelector;
import frc.robot.Commands.Auto.JoystickVisionAlign;
import frc.robot.Commands.Climber.ClimberDeploy;
import frc.robot.Commands.Climber.ClimberVertical;
import frc.robot.Commands.Climber.GoToClimbPosition;
import frc.robot.Commands.Climber.GoToFullDownPosition;
import frc.robot.Commands.Climber.JoystickClimber;
import frc.robot.Commands.Drivetrain.CompressorCommand;
import frc.robot.Commands.Drivetrain.HighGear;
import frc.robot.Commands.Drivetrain.JoystickDrive;
import frc.robot.Commands.Drivetrain.LowGear;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.IntakeRetract;
import frc.robot.Commands.Intake.ReverseCenterIntake;
import frc.robot.Commands.Intake.RunCenterIntake;
import frc.robot.Commands.Intake.StopIntake;
import frc.robot.Commands.Intake.StoreIntakeCommandGroup;
import frc.robot.Commands.Serializer.ReverseSerializer;
import frc.robot.Commands.Serializer.RunSerializer;
import frc.robot.Commands.Serializer.StopSerializer;
import frc.robot.Commands.Shooter.JoystickShooter;
import frc.robot.Commands.Shooter.OPZone1CommandGroup;
import frc.robot.Subsystems.AirCompressor;
import frc.robot.Subsystems.CenterIntake;
import frc.robot.Subsystems.ClimberPiston;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.IntakePiston;
import frc.robot.Subsystems.LeftClimber;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.RightClimber;
import frc.robot.Subsystems.Serializer;
import frc.robot.Subsystems.Shifter;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.ShooterPiston;

public class RobotContainer {

    public static Drivetrain drivetrain = new Drivetrain();
    public static Shifter shifter = new Shifter();
    public static Intake intake = new Intake();
    public static CenterIntake centerIntake = new CenterIntake();
    public static Serializer serializer = new Serializer();
    public static Shooter shooter = new Shooter();
    public static ShooterPiston shooterPiston = new ShooterPiston();
    public static LeftClimber leftClimber = new LeftClimber();
    public static RightClimber rightClimber = new RightClimber();
    public static IntakePiston intakePiston = new IntakePiston();
    public static ClimberPiston climberPiston = new ClimberPiston();
    public static AirCompressor airCompressor = new AirCompressor();
    public static Limelight limelight = new Limelight();

    /* Autonomous Selector */
    private final AutonomousSelector autonomousSelector = new AutonomousSelector();

    // Joysticks
    public Joystick Driver;
    public Joystick Operator;

    // Rumble Data
    public static final  long RUMBLE_MILLIS = 250;
    public static final double RUMBLE_INTENSITY = 1.0;

    //Driver Buttons
    public JoystickButton lowGearButton;
    public JoystickButton highGearButton;
    public JoystickButton intakeButton;
    public JoystickButton shootButton;
    public JoystickButton spitBallButton;
    public JoystickButton joystickAutoAlignButton;
    
    // Operator Buttons
    public JoystickButton climbButton;
    public JoystickButton climberUpButton;
    public JoystickButton climberPistonDownButton;
    public JoystickButton climberPistonVerticalButton;
    public JoystickButton fenderHighGoalButton;
  
    public RobotContainer() {
        Driver = new Joystick(0);
        Operator = new Joystick(1);

        configureButtonBindings();
        
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.drivetrain, new JoystickDrive());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.shifter, new LowGear());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.intake, new StopIntake());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.centerIntake, new StopCenterIntake());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.intakePiston, new IntakeRetract());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.serializer, new StopSerializer());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.shooter, new JoystickShooter());
        // CommandScheduler.getInstance().setDefaultCommand(RobotContainer.shooterPiston, new StoreShooterPiston());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.leftClimber, new JoystickClimber());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.rightClimber, new JoystickClimber());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.climberPiston, new ClimberDeploy());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.airCompressor, new CompressorCommand());
    }

    private void configureButtonBindings() {
        // Driver Buttons
        lowGearButton = new JoystickButton(Driver, 3);
        lowGearButton.whenPressed(new LowGear());

        highGearButton = new JoystickButton(Driver, 4);
        highGearButton.whenPressed(new HighGear());

        intakeButton = new JoystickButton(Driver, 5);
        intakeButton.whileHeld(new IntakeBallCommandGroup());
        intakeButton.whenReleased(new StoreIntakeCommandGroup());

        shootButton = new JoystickButton(Driver, 6);
        shootButton.whileHeld(new RunSerializer().alongWith(new RunCenterIntake()));
        shootButton.whenReleased(new StopSerializer().alongWith(new StopCenterIntake()));

        spitBallButton = new JoystickButton(Driver, 2);
        spitBallButton.whileHeld(new ReverseCenterIntake().alongWith(new ReverseSerializer()));
        spitBallButton.whenReleased(new StopCenterIntake().alongWith(new StopSerializer()));



        joystickAutoAlignButton = new JoystickButton(Driver, 1);
        joystickAutoAlignButton.whileHeld(new JoystickVisionAlign());


        //Operator Buttons
        climbButton = new JoystickButton(Operator, 1);
        climbButton.whenPressed(new GoToFullDownPosition());

        climberUpButton = new JoystickButton(Operator, 2);
        climberUpButton.whenPressed(new GoToClimbPosition());

        climberPistonDownButton = new JoystickButton(Operator, 5);
        climberPistonDownButton.whenPressed(new ClimberDeploy());

        climberPistonVerticalButton = new JoystickButton(Operator, 6);
        climberPistonVerticalButton.whenPressed(new ClimberVertical());

        fenderHighGoalButton = new JoystickButton(Operator, 3);
        fenderHighGoalButton.whenPressed(new OPZone1CommandGroup());
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

    public Command getAutonomousCommand() {
        return autonomousSelector.getCommand();
      }
}