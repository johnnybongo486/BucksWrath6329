package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.HighGear;
import frc.robot.Commands.JoystickDrive;
import frc.robot.Commands.LowGear;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Shifter;

public class RobotContainer {

    public static Drivetrain drivetrain = new Drivetrain();
    public static Shifter shifter = new Shifter();
    public Joystick Driver;

    public static final  long RUMBLE_MILLIS = 250;
    public static final double RUMBLE_INTENSITY = 1.0;

    public JoystickButton lowGearButton;
    public JoystickButton highGearButton;
  
    public RobotContainer() {
        Driver = new Joystick(0);
        configureButtonBindings();
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.drivetrain, new JoystickDrive());
        CommandScheduler.getInstance().setDefaultCommand(RobotContainer.shifter, new LowGear());
    }

    private void configureButtonBindings() {
        lowGearButton = new JoystickButton(Driver, 5);
        lowGearButton.whenPressed(new LowGear());

        highGearButton = new JoystickButton(Driver, 6);
        highGearButton.whenPressed(new HighGear());
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