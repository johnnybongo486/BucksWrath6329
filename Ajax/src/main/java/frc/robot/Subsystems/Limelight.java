package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

   private double ta;
   private double tx;
   private double ty;

   NetworkTableEntry prelimtx;
   NetworkTableEntry prelimty;
   NetworkTableEntry prelimta;
   NetworkTableEntry prelimCamtran;
   NetworkTable table;
   NetworkTableInstance Inst;

   public Limelight() {
      Inst = NetworkTableInstance.getDefault();
      table = Inst.getTable("limelight");
      prelimta = table.getEntry("ta");
      prelimtx = table.getEntry("tx");
      prelimty = table.getEntry("ty");
   }

   public void updateGameState(){
      ta = prelimta.getDouble(0);
      tx = prelimtx.getDouble(0);
      ty = prelimty.getDouble(0);
   }

   public double getArea(){
      ta = prelimta.getDouble(0);
      return ta;
   }

   public double getX(){
      tx = prelimtx.getDouble(0);
      return tx;
   }

   public double getY(){
      ty = prelimty.getDouble(0);
      return ty;
   }

   public void visionMode(){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
   }

   public void cameraMode(){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
   }

   public void updateDashboard() {
		SmartDashboard.putNumber("ta", getArea());
      SmartDashboard.putNumber("tx", getX());
      SmartDashboard.putNumber("ty", getY());
	}
}
