package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.Drivetrain.JoystickAutoAlign;
import frc.robot.Commands.Drivetrain.JoystickAutoAlignIntake;
import frc.robot.Commands.Drivetrain.PIDVisionFollowIntake;
import frc.robot.Commands.Drivetrain.PIDVisionFollowZone1;
import frc.robot.Commands.Drivetrain.PIDVisionFollowZone2;
import frc.robot.Commands.Drivetrain.PIDVisionFollowZone3;
import frc.robot.Commands.Drivetrain.PIDVisionFollowZone4;
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
    public JoystickButton IntakeAimButton;
    public JoystickButton AimZone1Button;
    public JoystickButton AimZone2Button;
    public JoystickButton AimZone3Button;
    public JoystickButton AimZone4Button;

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

        // Driver Buttons
        ShootButton = new JoystickButton(Driver, 6);
        ShootButton.whileHeld(new ShootBallCommandGroup());
        ShootButton.whenReleased(new StopBallCommandGroup());

        IntakeButton = new JoystickButton(Driver, 5);
        IntakeButton.whileHeld(new IntakeBallCommandGroup());
        IntakeButton.whenReleased( new IntakeStop());

        ShooterAimButton = new JoystickButton(Driver, 1);
        ShooterAimButton.whileHeld(new JoystickAutoAlign());

        IntakeAimButton = new JoystickButton (Driver, 2);
        IntakeAimButton.whileHeld(new JoystickAutoAlignIntake());

        AimZone1Button = new JoystickButton(Driver, 7);
        AimZone1Button.whileHeld(new PIDVisionFollowZone1());

        AimZone2Button = new JoystickButton(Driver, 8);
        AimZone2Button.whileHeld(new PIDVisionFollowZone2());

        AimZone3Button = new JoystickButton(Driver, 3);
        AimZone3Button.whileHeld(new PIDVisionFollowZone3());

        AimZone4Button = new JoystickButton(Driver, 4);
        AimZone4Button.whileHeld(new PIDVisionFollowZone4());

        // Only For Testing
        //VisionIntakeButton = new JoystickButton(Driver, 4);
        //VisionIntakeButton.whileHeld(new PIDVisionFollowIntake());

        // Operator Buttons
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