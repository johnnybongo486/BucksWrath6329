package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.Commands.Drivetrain.LowGear;


public class Shifter extends Subsystem {
    
    private final Solenoid shifterSol = new Solenoid(0, 5);

    public void initDefaultCommand() {
        setDefaultCommand(new LowGear());
    }

    public void lowGear() {
        shifterSol.set(false);
        Robot.Drivetrain.setIsHighGear(false);
    }

    public void highGear() {
        shifterSol.set(true);
        Robot.Drivetrain.setIsHighGear(true);
    }
}