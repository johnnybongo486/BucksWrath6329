package frc.robot.commands.Drivetrain;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;


public class PIDTurnToAngle extends Command {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private Boolean robotCentricSup;
    private double slowSpeed = 0.3;
    private double elevatorHeight = 0;

    public double rotationVal = 0;

    public double targetAngle = 0;
    public double currentAngle = 0;
    public double acceptableError = 0;

    private final PIDController angleController = new PIDController(0.012, 0, 0);

    public PIDTurnToAngle(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, Boolean robotCentricSup, double targetAngle) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.targetAngle = targetAngle;
        angleController.enableContinuousInput(0, 360);
    }

    public void initialize() {
        angleController.setTolerance(5);
    }

    @Override
    public void execute() {
        //TODO: I'm not sure if I disabled the elevator consideration here correctly.
        //elevatorHeight = RobotContainer.elevator.getCurrentPosition();
        currentAngle = s_Swerve.getYaw().getDegrees() + 180;
        rotationVal = angleController.calculate(currentAngle, targetAngle);
        
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);

        /*if (elevatorHeight >= 30000) {
            translationVal = translationVal * slowSpeed;
            strafeVal = strafeVal * slowSpeed;
        }

        else if (elevatorHeight > 5000 && elevatorHeight < 29999) {
            translationVal = translationVal * 0.5;
            strafeVal = strafeVal * 0.5;
        }*/

        /* Drive */
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
            rotationVal * 4, 
            true, 
            true
        );
    }

    public boolean isFinished() {
        return false;//angleController.atSetpoint();
    }

    protected void end() {
        
    }

    protected void interrupted(){
        end();
    }
}
