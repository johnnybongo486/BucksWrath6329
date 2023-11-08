package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.Auto.Drivetrain.JoystickAutoAlign;
import frc.robot.Commands.Drivetrain.HighGear;
import frc.robot.Commands.Drivetrain.LowGear;
import frc.robot.Commands.Elevator.GoToClimbPosition;
import frc.robot.Commands.Elevator.GoToRaiseRobotPosition;
import frc.robot.Commands.Intake.IntakeBallCommandGroup;
import frc.robot.Commands.Intake.ReverseIntakeCommandGroup;
import frc.robot.Commands.Serializer.reverseSerializer;
import frc.robot.Commands.Shooter.*;
import frc.robot.Commands.WOF.SpinWOF;

public class OI {

    public Joystick Driver;
    public Joystick Operator;

    public JoystickButton AutoAlignButton;
    public JoystickButton VisionFollowButton;
    public JoystickButton JoystickAutoAlignButton;
    public JoystickButton CloseShooterButton;
    public JoystickButton IntakeButton;
    public JoystickButton ShooterButton;
    public JoystickButton OPShootCloseButton;
    public JoystickButton ReverseIntakeButton;
    public JoystickButton OPShootMidButton;
    public JoystickButton OPShootFarButton;
    public JoystickButton UpShiftButton;
    public JoystickButton DownShiftButton;
    public JoystickButton WOFButton;
    public JoystickButton DeployShooterPistonButton;
    public JoystickButton ReverseSerializerButton;
    public JoystickButton StoreShooterPistonButton;
    public JoystickButton ClimbPositionButton;
    public JoystickButton RaiseRobotButton;
    public JoystickButton AlignButton;

    public static final  long RUMBLE_MILLIS = 250;
    public static final double RUMBLE_INTENSITY = 1.0;

    public OI(){
    
        // Controllers
        Driver = new Joystick(0);
        Operator = new Joystick(1);
    
        // Driver Buttons
        ShooterButton = new JoystickButton(Driver, 6);
        ShooterButton.whileHeld(new ShootBallCommandGroup());
        ShooterButton.whenReleased(new StopShooter());

        CloseShooterButton = new JoystickButton(Driver, 2);
        CloseShooterButton.whileHeld(new CloseShootBallCommandGroup());
        CloseShooterButton.whenReleased(new StopShooter());
        
        UpShiftButton = new JoystickButton(Driver, 9);
        UpShiftButton.whenPressed(new HighGear());
        
        DownShiftButton = new JoystickButton(Driver, 10);
        DownShiftButton.whenPressed(new LowGear());

        AlignButton = new JoystickButton(Driver, 1);
        AlignButton.whileHeld(new JoystickAutoAlign());

        WOFButton = new JoystickButton(Driver, 3);
        WOFButton.whenPressed(new SpinWOF(600));
        // WOFButton.whenReleased(new StopWOF());
        
        IntakeButton = new JoystickButton(Driver, 5);
        IntakeButton.whileHeld(new IntakeBallCommandGroup());
        
        ReverseIntakeButton = new JoystickButton(Driver, 8);
        ReverseIntakeButton.whileHeld(new ReverseIntakeCommandGroup());

        // Operator Buttons
        DeployShooterPistonButton = new JoystickButton(Operator, 5);
        DeployShooterPistonButton.whenPressed(new DeployShooterPiston());

        StoreShooterPistonButton = new JoystickButton(Operator, 6);
        StoreShooterPistonButton.whenPressed(new StoreShooterPiston());

        OPShootCloseButton = new JoystickButton(Operator, 1);
        OPShootCloseButton.whenPressed(new OPShootBallCommandGroupClose());

        OPShootMidButton = new JoystickButton(Operator, 3);
        OPShootMidButton.whenPressed(new OPShootBallCommandGroupMid());

        OPShootFarButton = new JoystickButton(Operator, 4);
        OPShootFarButton.whenPressed(new OPShootBallCommandGroupFar());

        ReverseSerializerButton = new JoystickButton(Operator, 2);
        ReverseSerializerButton.whenPressed(new reverseSerializer());

        ClimbPositionButton = new JoystickButton(Operator, 7);
        ClimbPositionButton.whenPressed(new GoToClimbPosition());

        RaiseRobotButton = new JoystickButton(Operator, 8);
        RaiseRobotButton.whenPressed(new GoToRaiseRobotPosition()); 

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
