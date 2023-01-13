package frc.robot.Commands;

import frc.robot.Constants;
import frc.robot.Subsystems.Swerve;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopSwerve extends CommandBase {

    private double rotation;
    private Translation2d translation;
    private boolean fieldRelative;
    private boolean openLoop;
    
    private Swerve s_Swerve;
    private Joystick controller;
    private int translationAxis;
    private int strafeAxis;
    private int rotationAxis;

    /**
     * Driver control
     */
    public TeleopSwerve(Swerve s_Swerve, Joystick controller, int translationAxis, int strafeAxis, int rotationAxis, boolean fieldRelative, boolean openLoop) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.controller = controller;
        this.translationAxis = translationAxis;
        this.strafeAxis = strafeAxis;
        this.rotationAxis = rotationAxis;
        this.fieldRelative = fieldRelative;
        this.openLoop = openLoop;
    }

    @Override
    public void execute() {
        double yAxis = -controller.getRawAxis(translationAxis);
        double xAxis = -controller.getRawAxis(strafeAxis);
        double rAxis = -controller.getRawAxis(rotationAxis);
        
        /* Deadbands */
        yAxis = (Math.abs(yAxis) < Constants.Swerve.stickDeadband) ? 0 : yAxis;
        xAxis = (Math.abs(xAxis) < Constants.Swerve.stickDeadband) ? 0 : xAxis;
        rAxis = (Math.abs(rAxis) < 0.05) ? 0 : rAxis;

        // Squared Inputs
        if (xAxis < 0) {
            xAxis = -(xAxis*xAxis);
        }
        else {
            xAxis = xAxis*xAxis;
        }

        if (yAxis < 0) {
            yAxis = -(yAxis*yAxis);
        }
        else {
            yAxis = yAxis*yAxis;
        }

        if (rAxis < 0) {
            rAxis = -(rAxis*rAxis);
        }
        else {
            rAxis = rAxis*rAxis;
        }

        translation = new Translation2d(yAxis, xAxis).times(Constants.Swerve.maxSpeed);
        rotation = rAxis * Constants.Swerve.maxAngularVelocity;
        s_Swerve.drive(translation, rotation, fieldRelative, openLoop);
    }
}
