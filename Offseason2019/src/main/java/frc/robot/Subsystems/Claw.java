package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import frc.robot.Commands.Claw.CloseClaw;

public class Claw extends Subsystem {
    
    private final Solenoid clawSol = RobotMap.clawSol;

    public void initDefaultCommand() {
        setDefaultCommand(new CloseClaw());
    }

    public void Open() {
        clawSol.set(true);
    }

    public void Closed() {
        clawSol.set(false);
    }
}