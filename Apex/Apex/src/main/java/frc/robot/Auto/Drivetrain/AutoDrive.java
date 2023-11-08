package frc.robot.Auto.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoDrive extends Command {

     public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
     public double leftRotations = 0;
     public double rightRotations = 0;

     // Smart Motion Coefficients
     // maxVel = 2000; // rpm
     // maxAcc = 1500;

     public AutoDrive(double Distance, double maxSpeed, double maxAcceleration) {
        requires(Robot.Drivetrain);
        this.leftRotations = Distance * 6430;
        this.rightRotations = Distance * 6430;
        this. maxVel = maxSpeed;
        this.maxAcc = maxAcceleration;
     }

     protected void initialize() {
          Robot.Drivetrain.resetDriveEncoders();
          // PID coefficients
          kP = 0.0175; 
		kI = 0.000005;
		kD = .05; 
		kIz = 12; 
		kFF = 0; 
		kMaxOutput = 1; 
		kMinOutput = -1;
          maxRPM = 5700;

          // set PID coefficients
          Robot.Drivetrain.leftLeader_pidController.setP(kP,1);
          Robot.Drivetrain.leftLeader_pidController.setI(kI,1);
          Robot.Drivetrain.leftLeader_pidController.setD(kD,1);
          Robot.Drivetrain.leftLeader_pidController.setIZone(kIz,1);
          Robot.Drivetrain.leftLeader_pidController.setFF(kFF,1);
          Robot.Drivetrain.leftLeader_pidController.setOutputRange(kMinOutput, kMaxOutput,1);

          Robot.Drivetrain.rightLeader_pidController.setP(kP,1);
          Robot.Drivetrain.rightLeader_pidController.setI(kI,1);
          Robot.Drivetrain.rightLeader_pidController.setD(kD,1);
          Robot.Drivetrain.rightLeader_pidController.setIZone(kIz,1);
          Robot.Drivetrain.rightLeader_pidController.setFF(kFF,1);
          Robot.Drivetrain.rightLeader_pidController.setOutputRange(kMinOutput, kMaxOutput,1);

    /**
     * Smart Motion coefficients are set on a CANPIDController object
     * 
     * - setSmartMotionMaxVelocity() will limit the velocity in RPM of
     * the pid controller in Smart Motion mode
     * - setSmartMotionMinOutputVelocity() will put a lower bound in
     * RPM of the pid controller in Smart Motion mode
     * - setSmartMotionMaxAccel() will limit the acceleration in RPM^2
     * of the pid controller in Smart Motion mode
     * - setSmartMotionAllowedClosedLoopError() will set the max allowed
     * error for the pid controller in Smart Motion mode
     */
          int smartMotionSlot = 1;

          Robot.Drivetrain.leftLeader_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
          Robot.Drivetrain.leftLeader_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
          Robot.Drivetrain.leftLeader_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
          Robot.Drivetrain.leftLeader_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

          Robot.Drivetrain.rightLeader_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
          Robot.Drivetrain.rightLeader_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
          Robot.Drivetrain.rightLeader_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
          Robot.Drivetrain.rightLeader_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
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

