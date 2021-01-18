package frc.robot.Commands.Drivetrain.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoDrive extends Command {

     public double kP, kI, kD, kIz, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
     public double leftRotations = 0;
     public double rightRotations = 0;

     public AutoDrive(double Distance, double maxSpeed, double maxAcceleration) {
        requires(Robot.Drivetrain);
        this.leftRotations = Distance * 6430;
        this.rightRotations = Distance * 6430;
        this.maxVel = maxSpeed;
        this.maxAcc = maxAcceleration;
     }

     protected void initialize() {
          Robot.Drivetrain.resetDriveEncoders();
          // PID coefficients
          kP = 0.0175; 
	     kI = 0.000005;
		kD = .05; 
		kIz = 12; 
		kF = 0; 
		kMaxOutput = 1; 
		kMinOutput = -1;
          maxRPM = 5700;

          // set PID coefficients
          Robot.Drivetrain.leftLead.configPIDF(0, kP, kI, kD, kF);
          Robot.Drivetrain.rightLead.configPIDF(0, kP, kI, kD, kF);
          Robot.Drivetrain.leftLead.configPeakOutputForward(kMaxOutput);
          Robot.Drivetrain.leftLead.configPeakOutputReverse(kMinOutput);
          Robot.Drivetrain.rightLead.configPeakOutputForward(kMaxOutput);
          Robot.Drivetrain.rightLead.configPeakOutputReverse(kMinOutput);

          Robot.Drivetrain.leftLead.configMotionCruiseVelocity(maxVel, 10);
          Robot.Drivetrain.leftLead.configMotionAcceleration(maxAcc, 10);
          Robot.Drivetrain.leftLead.configAllowableClosedloopError(0, allowedErr);
          Robot.Drivetrain.rightLead.configMotionCruiseVelocity(maxVel, 10);
          Robot.Drivetrain.rightLead.configMotionAcceleration(maxAcc, 10);
          Robot.Drivetrain.rightLead.configAllowableClosedloopError(0, allowedErr);
   }

   protected void execute() {
          Robot.Drivetrain.smartDrive(leftRotations, rightRotations);
   }

   protected boolean isFinished() {
          return false;
   }

   protected void end() {
        Robot.Drivetrain.stopDrivetrain();
   }

   protected void interrupted(){
        end();
   }
  
}