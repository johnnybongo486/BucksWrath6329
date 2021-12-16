package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID;
    
    public class OI {
    
        public Joystick Driver;
    
        public static final  long RUMBLE_MILLIS = 250;
        public static final double RUMBLE_INTENSITY = 1.0;
    
        public OI(){
            // Controllers
            Driver = new Joystick(0);
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
