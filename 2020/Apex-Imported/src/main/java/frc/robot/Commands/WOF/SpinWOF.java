package frc.robot.Commands.WOF;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SpinWOF extends Command {

    public double targetDistance = 0;
    public double currentDistance = 0;
    public double acceptableError = 6;
    public double error = 0;

   public SpinWOF(double rotations) {
          requires(Robot.WOF);
          this.error = 0;
          this.targetDistance = rotations;
   }

   protected void initialize() {
          Robot.WOF.resetWOFEncoders();
          Timer.delay(0.1);
   }

   protected void execute() {
          Robot.WOF.autoRotate(targetDistance);
          currentDistance = Robot.WOF.getWOFTicks();
          error = targetDistance - currentDistance;
   }

   protected boolean isFinished() {
        return Math.abs(error) < acceptableError;
   }

   protected void end() {
        Robot.WOF.stopWOF();
   }

   protected void interrupted(){
       end();
   }
}

