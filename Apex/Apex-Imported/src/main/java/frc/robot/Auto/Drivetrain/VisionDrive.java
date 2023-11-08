package frc.robot.Auto.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import edu.wpi.first.networktables.*;

public class VisionDrive extends Command {

    private double tx;
    private double ta;

    private double left_command;
    private double right_command;

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimta;
    NetworkTable table;
    NetworkTableInstance Inst;

    public VisionDrive() {
        requires(Robot.Drivetrain);

        left_command = 0;
        right_command = 0;

    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
        prelimtx = table.getEntry("tx");
        prelimta = table.getEntry("ta");
    }

    protected void execute() {
        tx = prelimtx.getDouble(0);
        ta = prelimta.getDouble(0);
        
        if (ta > 0) {
            ta = Math.sqrt(ta);
            left_command = 1 - ta/2.5;
            right_command = left_command;

            left_command = left_command + tx/80;
            right_command = right_command - tx/80;
        }

        else {

        }
        
		Robot.Drivetrain.setPower(left_command, right_command);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.Drivetrain.stopDrivetrain();
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    protected void interrupted(){
        end();
    }
}