package frc.robot.Auto.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class NeoAutoDrive extends Command {

    public double leftTargetDistance = 0;
    public double leftCurrentDistance = 0;
    public double rightTargetDistance = 0;
    public double rightCurrentDistance = 0;
    public double ftPERrotation = (Math.PI * 6) / 12;
    public double acceptableError = 6;
    public double leftError = 0;
    public double rightError = 0;

   public NeoAutoDrive(double leftDistance, double rightDistance) {
          requires(Robot.Drivetrain);
          this.leftTargetDistance = (leftDistance * 12);
          this.rightTargetDistance = (rightDistance * 12); 
          this.leftError = 0;
          this.rightError = 0;  
          
   }

   protected void initialize() {
          Robot.Drivetrain.resetDriveEncoders();
          Timer.delay(0.1);
          Robot.Drivetrain.leftLeader.setClosedLoopRampRate(0.25);
          Robot.Drivetrain.rightLeader.setClosedLoopRampRate(0.25);
   }

   protected void execute() {
          Robot.Drivetrain.autoDrive(leftTargetDistance, rightTargetDistance);
          leftCurrentDistance = Robot.Drivetrain.getLeftTicks();
          rightCurrentDistance = Robot.Drivetrain.getRightTicks();
          leftError = leftTargetDistance - leftCurrentDistance;
          rightError = rightTargetDistance - rightCurrentDistance;
   }

   protected boolean isFinished() {
        return Math.abs(leftError) < acceptableError && Math.abs(rightError) < acceptableError;
   }

   protected void end() {
        Robot.Drivetrain.stopDrivetrain();
        Robot.Drivetrain.leftLeader.setClosedLoopRampRate(0.25);
       Robot.Drivetrain.rightLeader.setClosedLoopRampRate(0.25);
   }

   protected void interrupted(){

   }
}

