package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.Auto.AutoVisionShooter;
import frc.robot.Commands.Auto.VisionFollow;
import frc.robot.Commands.Auto.VisionFollowIntake;
import frc.robot.Commands.Drivetrain.JoystickAutoAlign;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.IntakeStop;
import frc.robot.Commands.Serializer.ShootBallCommandGroup;
import frc.robot.Commands.Serializer.StopBallCommandGroup;
import frc.robot.Commands.Shooter.OPZone1CommandGroup;
import frc.robot.Commands.Shooter.OPZone2CommandGroup;
import frc.robot.Commands.Shooter.OPZone3CommandGroup;
import frc.robot.Commands.Shooter.OPZone4CommandGroup;

import edu.wpi.first.wpilibj.GenericHID;

public class OI {

    public Joystick Driver;
    public Joystick Operator;

    public JoystickButton VisionIntakeButton;
    public JoystickButton VisionShootButton;
    public JoystickButton ShooterAimButton;

    public JoystickButton IntakeButton;
    public JoystickButton ShootButton;
    public JoystickButton OPZone1Shoot;
    public JoystickButton OPZone2Shoot;
    public JoystickButton OPZone3Shoot;
    public JoystickButton OPZone4Shoot;


    public static final  long RUMBLE_MILLIS = 250;
    public static final double RUMBLE_INTENSITY = 1.0;

    public OI(){
    
        // Controllers
        Driver = new Joystick(0);
        Operator = new Joystick(1);

        ShootButton = new JoystickButton(Driver, 6);
        ShootButton.whileHeld(new ShootBallCommandGroup());
        ShootButton.whenReleased(new StopBallCommandGroup());

        IntakeButton = new JoystickButton(Driver, 5);
        IntakeButton.whileHeld(new IntakeBallCommandGroup());
        IntakeButton.whenReleased( new IntakeStop());

        VisionIntakeButton = new JoystickButton (Driver, 2);
        VisionIntakeButton.whileHeld(new VisionFollowIntake());

        VisionShootButton = new JoystickButton(Driver, 3);
        VisionShootButton.whileHeld(new VisionFollow());

        VisionIntakeButton = new JoystickButton(Driver, 4);
        VisionIntakeButton.whileHeld(new VisionFollowIntake());

        ShooterAimButton = new JoystickButton(Driver, 1);
        ShooterAimButton.whileHeld(new JoystickAutoAlign());

        OPZone1Shoot = new JoystickButton(Operator, 1);
        OPZone1Shoot.whenPressed(new OPZone1CommandGroup());

        OPZone2Shoot = new JoystickButton(Operator, 2);
        OPZone2Shoot.whenPressed(new OPZone2CommandGroup());

        OPZone3Shoot = new JoystickButton(Operator, 3);
        OPZone3Shoot.whenPressed(new OPZone3CommandGroup());

        OPZone4Shoot = new JoystickButton(Operator, 4);
        OPZone4Shoot.whenPressed(new OPZone4CommandGroup());
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

    public double getDriverLeftStickX() {
        return stickDeadband(this.Driver.getRawAxis(0), 0.1, 0.0);
    }
    
    public double getDriverRightStickX() {
        return stickDeadband(this.Driver.getRawAxis(4), 0.05, 0.0);
    }
    
    public double getOperatorLeftStickY() {
        return stickDeadband(this.Operator.getRawAxis(1), 0.1, 0.0);
    }
    
    public double getOperatorRightStickY() {
        return stickDeadband(this.Operator.getRawAxis(5), 0.1, 0.0);
    }

    public void rumbleLeft() {
        Driver.setRumble(GenericHID.RumbleType.kLeftRumble, OI.RUMBLE_INTENSITY);
    }

    public void rumbleRight() {
        Driver.setRumble(GenericHID.RumbleType.kRightRumble, OI.RUMBLE_INTENSITY);
    }

    public void stopRumble() {
        Driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        Driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    }
}