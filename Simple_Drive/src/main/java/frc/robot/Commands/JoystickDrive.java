package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Drivetrain;

public class JoystickDrive extends CommandBase {
    
    private Drivetrain drivetrain;
    private double speed = 0;
    private double turn = 0;

    public JoystickDrive(Drivetrain drivetrain, double speed, double turn) {
        this.speed = speed;
        this.turn = turn;
        drivetrain = drivetrain;
        
        addRequirements(drivetrain);
    }

    public void initialize() {
    }

    @Override
    public void execute() {
        drivetrain.teleopDrive(speed, turn);
    }

    public boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        SmartDashboard.putBoolean("Drive Command Interrupted", true);
    }
}
