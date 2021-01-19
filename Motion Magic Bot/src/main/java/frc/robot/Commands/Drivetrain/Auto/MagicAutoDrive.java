package frc.robot.Commands.Drivetrain.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MagicAutoDrive extends Command {

     public double kP, kI, kD, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
     public double tkP, tkI, tkD, tkF, tkMaxOutput, tkMinOutput, tmaxRPM, tmaxVel, tminVel, tmaxAcc, tallowedErr;

     public double leftRotations = 0;
     public double rightRotations = 0;
     public double angle = 0;
     public double ticks = 0;
     public int kIz;
     public int tkIz;

     public MagicAutoDrive(double distance, double maxSpeed, double maxAcceleration, double targetAngle) {
        requires(Robot.Drivetrain);
        this.ticks = distance * 6430;
        this.maxVel = maxSpeed;
        this.maxAcc = maxAcceleration;
        this.angle = targetAngle;
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

          tkP = 0.0175; 
	     tkI = 0.000005;
		tkD = .05; 
		tkIz = 12; 
		tkF = 0; 
		tkMaxOutput = 1; 
          tkMinOutput = -1;
          
          maxRPM = 5700;

          // set PID coefficients

        /* FPID for Distance */
          Robot.Drivetrain.rightConfig.slot0.kF = kF;
		Robot.Drivetrain.rightConfig.slot0.kP = kP;
		Robot.Drivetrain.rightConfig.slot0.kI = kI;
		Robot.Drivetrain.rightConfig.slot0.kD = kD;
		Robot.Drivetrain.rightConfig.slot0.integralZone = kIz;
          Robot.Drivetrain.rightConfig.slot0.closedLoopPeakOutput = kMaxOutput;
        
        /* FPID for Heading */
          Robot.Drivetrain.rightConfig.slot1.kF = tkF;
		Robot.Drivetrain.rightConfig.slot1.kP = tkP;
		Robot.Drivetrain.rightConfig.slot1.kI = tkI;
		Robot.Drivetrain.rightConfig.slot1.kD = tkD;
		Robot.Drivetrain.rightConfig.slot1.integralZone = tkIz;
          Robot.Drivetrain.rightConfig.slot1.closedLoopPeakOutput = tkMaxOutput;
        /* Motion Magic Configs */
          Robot.Drivetrain.rightConfig.motionAcceleration = 2000; //(distance units per 100 ms) per second
          Robot.Drivetrain.rightConfig.motionCruiseVelocity = 2000; //distance units per 100 ms


        /* APPLY the config settings */
          Robot.Drivetrain.leftLead.configAllSettings(Robot.Drivetrain.leftConfig);
          Robot.Drivetrain.rightLead.configAllSettings(Robot.Drivetrain.rightConfig);

          Robot.Drivetrain.rightLead.selectProfileSlot(0, 0);
	     Robot.Drivetrain.rightLead.selectProfileSlot(1, 1);
   }

   protected void execute() {
          Robot.Drivetrain.magicDrive(ticks, angle);
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