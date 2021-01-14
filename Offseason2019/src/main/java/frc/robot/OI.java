package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.Commands.JoystickAutoAlign;
import frc.robot.Commands.VisionFollow;
import frc.robot.Commands.CommandGroups.PickupCargo;
import frc.robot.Commands.CommandGroups.PickupHatch;
import frc.robot.Commands.CommandGroups.ReadyHatch;
import frc.robot.Commands.CommandGroups.ScoreCargo;
import frc.robot.Commands.CommandGroups.ScoreHatch;
import frc.robot.Commands.CommandGroups.StoreCargo;
import frc.robot.Commands.CommandGroups.StoreHatch;
import frc.robot.Commands.CommandGroups.StoreRocketCargo;
import frc.robot.Commands.Wrist.GoToCargoPosition;
import frc.robot.Commands.Wrist.ZeroWrist;

public class OI {

    public Joystick Driver;
    public Joystick Operator;

    public JoystickButton AutoAlignButton;
    public JoystickButton VisionFollowButton;
    public JoystickButton JoystickAutoAlignButton;
    public JoystickButton HighGearButton;
    public JoystickButton LowGearButton;
    public JoystickButton IntakeButton;
    public JoystickButton HatchButton;
    public JoystickButton Level1CargoButton;
    public JoystickButton CargoshipButton;
    public JoystickButton HomeButton;

    public JoystickButton ScoreHatchButton;
    public JoystickButton ScoreCargoButton;
    public JoystickButton IntakeCargoButton;
    public JoystickButton IntakeHatchButton;

    
    public static final long rumbleMillis = 250;
    public static final double rumbleIntensity = 1.0;

    public OI() {
    
        // Controllers
        Driver = new Joystick(0);
        Operator = new Joystick(1);

        // Driver Buttons

        IntakeCargoButton = new JoystickButton(Driver, 6);
        IntakeCargoButton.whileHeld(new PickupCargo());

        IntakeCargoButton = new JoystickButton(Driver, 6);
        IntakeCargoButton.whenReleased(new StoreCargo());
        
        IntakeHatchButton = new JoystickButton(Driver, 5);
        IntakeHatchButton.whileHeld(new PickupHatch());

        IntakeHatchButton = new JoystickButton(Driver, 5);
        IntakeHatchButton.whenReleased(new StoreHatch());

        ScoreHatchButton = new JoystickButton(Driver, 1);
        ScoreHatchButton.whileHeld(new ScoreHatch());

        ScoreCargoButton = new JoystickButton(Driver, 2);
        ScoreCargoButton.whileHeld(new ScoreCargo());

        AutoAlignButton = new JoystickButton(Driver, 3);
        AutoAlignButton.whileHeld(new JoystickAutoAlign());

        VisionFollowButton = new JoystickButton(Driver, 4);
        VisionFollowButton.whileHeld(new VisionFollow());

        // Operator Buttons

        HomeButton = new JoystickButton(Operator, 1);
        HomeButton.whenPressed(new ZeroWrist());

        HatchButton = new JoystickButton(Operator, 2);
        HatchButton.whenPressed(new ReadyHatch());

        Level1CargoButton = new JoystickButton(Operator, 3);
        Level1CargoButton.whenPressed(new StoreRocketCargo());

        CargoshipButton = new JoystickButton(Operator, 4);
        CargoshipButton.whenPressed(new GoToCargoPosition());
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
        return stickDeadband(this.Operator.getRawAxis(1), 0.05, 0.0);
    }


    public void rumbleLeft() {
        Driver.setRumble(GenericHID.RumbleType.kLeftRumble, OI.rumbleIntensity);
    }

    public void rumbleRight() {
        Driver.setRumble(GenericHID.RumbleType.kRightRumble, OI.rumbleIntensity);
    }

    public void stopRumble() {
        Driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        Driver.setRumble(GenericHID.RumbleType.kRightRumble, 0);
    }
    
}

