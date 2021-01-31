package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import edu.wpi.first.networktables.*;

public class AutoFindTarget extends Command {
    private double runTime = 0.5;

    private double ta;

    NetworkTableEntry prelimta;
    NetworkTable table;
    NetworkTableInstance Inst;

    public AutoFindTarget() {
        requires(Robot.Drivetrain);
    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
        prelimta = table.getEntry("ta");
    }

    protected void execute() {
        ta = prelimta.getDouble(0);
        
        if (ta > 0) {
            if (ta > 1){
                Robot.Drivetrain.setIsRed(true);
            }
            else {
                Robot.Drivetrain.setIsRed(false);
            }

        }

        else {

        }
        
    }

    protected boolean isFinished() {
        return this.timeSinceInitialized() >= runTime;
    }

    protected void end() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    protected void interrupted(){
        end();
    }
}