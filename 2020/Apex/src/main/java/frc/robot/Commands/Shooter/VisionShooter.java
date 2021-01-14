package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import edu.wpi.first.networktables.*;

public class VisionShooter extends Command {

    private double ta;
    private double conversionFactor = 4096 / 600;
    private double shooter_speed;
    private double runTime = 3;


    NetworkTableEntry prelimta;
    NetworkTable table;
    NetworkTableInstance Inst;

    public VisionShooter() {
        requires(Robot.Shooter);
        shooter_speed = 0;
    }
    
    protected void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        Inst = NetworkTableInstance.getDefault();
        table = Inst.getTable("limelight");
        prelimta = table.getEntry("ta");
        Timer.delay(1);
        ta = prelimta.getDouble(0);
        ta = Math.sqrt(ta);
        ta = 1/ta;
        if (ta > 0) {
            shooter_speed = 2100*ta*conversionFactor;            
            Robot.Shooter.setTargetVelocity(shooter_speed); //maybe should be in execute, so it is constantly being reset? Might cause errors.
        }
        else {

        }

    }

    protected void execute() {

        Robot.Shooter.velocityControl();
    }

    protected boolean isFinished() {
        return Robot.Shooter.isAtVelocity(shooter_speed) || this.timeSinceInitialized() >= runTime;

    }

    protected void end() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    protected void interrupted(){
        end();
    }
}