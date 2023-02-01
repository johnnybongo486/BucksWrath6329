package frc.robot.commands.Drivetrain;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TurnToAngle extends CommandBase {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private Boolean robotCentricSup;

    public double rotationVal = 0;
    public double rotateGain = 0;

    public double kPgain = 0.008; // 0.01
    public double lPgain = 0.00; /* percent throttle per degree of error */ //was .04
	public double kDgain = 0.075; /* percent throttle per angular velocity dps */
	public double kMaxCorrectionRatio = 1; /* cap corrective turning throttle to 30 percent of forward throttle was 0.3*/

    public double targetAngle = 0;
    public double currentAngle = 0;
    public double currentAngularRate = 0;
    public double currentDistance = 0;
    public double error = 0;
    public double acceptableError = 5;
    public double maxSpeed = 0;

    public TurnToAngle(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, Boolean robotCentricSup, double targetAngle) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.targetAngle = targetAngle;
    }

    @Override
    public void execute() {
        currentAngle = s_Swerve.getYaw().getDegrees();
        rotationVal = (targetAngle - currentAngle) * kPgain;
        error = Math.abs(targetAngle - currentAngle);
        double maxThrot = MaxCorrection(rotationVal, kMaxCorrectionRatio);
        rotationVal = Cap(rotationVal, maxThrot);
        
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);

        /* Drive */
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
            rotationVal * Constants.Swerve.maxAngularVelocity, 
            !robotCentricSup, 
            true
        );
    }

    public boolean isFinished() {
        return error < acceptableError;
    }

    protected void end() {
        
    }

    protected void interrupted(){
        end();
    }

    double MaxCorrection(double forwardThrot, double scalor) {
        /* make it positive */
        if(forwardThrot < 0) {forwardThrot = -forwardThrot;}
        /* max correction is the current forward throttle scaled down */
        forwardThrot *= scalor;
        /* ensure caller is allowed at least 10% throttle,
        * regardless of forward throttle */
        if(forwardThrot < 1)    // was 0.3 then 0.7
            return 1;           // was 0.3 then 0.7
        return forwardThrot;    
    }

    double Cap(double value, double peak) {
        if (value < -peak)
            return -peak;
        if (value > +peak)
            return +peak;
        return value;
    }
}
