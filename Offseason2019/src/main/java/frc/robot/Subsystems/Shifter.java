package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.*;

public class Shifter extends Subsystem {
    
    private final Solenoid shifterSol = RobotMap.shifterSol;

    public void initDefaultCommand() {
        setDefaultCommand(new AutoShifter());
    }

    public void LowGear() {
        shifterSol.set(false);
        Robot.Drivetrain.setIsHighGear(false);
    }

    public void HighGear() {
        shifterSol.set(false);
        Robot.Drivetrain.setIsHighGear(true);
    }
}