package frc.robot.Commands.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DeployZone1ShooterPiston extends CommandBase {

    private double runTime = 0.25;
    private double m_startTime = -1;

    public DeployZone1ShooterPiston() {
        addRequirements(RobotContainer.shooterPiston);
    }

    public void initialize() {
        RobotContainer.shooterPiston.zone1Deploy();
        m_startTime = Timer.getFPGATimestamp();
    }

    public void execute() {
        RobotContainer.shooterPiston.zone1Deploy();
    }

   public boolean isFinished() {
       return false || this.timeSinceInitialized() >= runTime;
   }

   protected void end() {
      
   }

   protected void interrupted(){

   }

   public synchronized final double timeSinceInitialized() {
    return m_startTime < 0 ? 0 : Timer.getFPGATimestamp() - m_startTime;
    }

}
