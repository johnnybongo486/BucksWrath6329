package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetShooterPiston extends CommandBase {
    private double setPosition = 0;

    public SetShooterPiston(double position) {
        this.setPosition = position;
        addRequirements(RobotContainer.shooterPiston);
    }

    public void initialize() {
        //RobotContainer.shooterPiston.setPosition(setPosition);
    }

    public void execute() {
        RobotContainer.shooterPiston.setPosition(setPosition);

    }

   public boolean isFinished() {
       return true;  // was false
   }

   protected void end() {
      
   }

   protected void interrupted(){

   }
}
