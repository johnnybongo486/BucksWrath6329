package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.*;

public class AutoVisionShooter extends Command {

    private double tx;
    private double ta;

    private double left_command;
    private double right_command;

    public double error = 0;
    public double acceptableError = .05;
    public double targetDistance = 1.6;

    NetworkTableEntry prelimtx;
    NetworkTableEntry prelimta;
    NetworkTable table;
    NetworkTableInstance Inst;

    public AutoVisionShooter() {
        requires(Robot.Drivetrain);

        left_command = 0;
        right_command = 0;

    }

    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("camMode").setNumber(0);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight-shooter");
        prelimtx = table.getEntry("tx");
        prelimta = table.getEntry("ta");
    }

    protected void execute() {
        tx = prelimtx.getDouble(0);
        ta = prelimta.getDouble(0);
        
        if (ta > 0) {
            ta = Math.sqrt(ta);
            left_command = 1 - ta/1.6;
            right_command = left_command;

            left_command = left_command + tx/40;
            right_command = right_command - tx/40;
        }

        else {

        }
        
        Robot.Drivetrain.drive(ControlMode.PercentOutput, left_command, right_command);
        error = Math.abs(targetDistance - ta);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.Drivetrain.drive(ControlMode.PercentOutput, 0, 0);
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("ledMode").setNumber(1);
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("camMode").setNumber(1);
    }

    protected void interrupted(){
        end();
    }
}