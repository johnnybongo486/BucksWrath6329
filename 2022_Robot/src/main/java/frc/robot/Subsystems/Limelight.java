package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Models.Buffer;
import frc.robot.Models.HelperFunctions;

public class Limelight extends SubsystemBase {

   private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

   private Buffer limelightbuffer;
   private NetworkTableEntry tx = table.getEntry("tx");
   private NetworkTableEntry ty = table.getEntry("ty");
   private NetworkTableEntry ta = table.getEntry("ta");
   private NetworkTableEntry ledMode = table.getEntry("ledMode");
   private NetworkTableEntry stream = table.getEntry("stream");

   private static double fovX = 59.6;
   private static double fovY = 49.7;

   public Limelight() {
      this.limelightbuffer = new Buffer(3);
   }

   public double getX() {
      return tx.getDouble(0.0);
   }

   public double getSmoothX() {
      return HelperFunctions.mean(limelightbuffer.toArray());
   }

   public double getXProportional() {
      return getX() / (getFovX() / 2);
   }

   public double getY() {
      return ty.getDouble(0.0);
   }

   public double getArea() {
      return ta.getDouble(0.0);
   }

   public boolean setLedModeOn() {
      return this.ledMode.setNumber(3);// 3 is Limelight LED force on
   }

   public boolean setLedModeOff() {
      return this.ledMode.setNumber(1);// 1 is Limelight LED force off
   }

   public double getFovX() {
      return fovX;
   }

   public double getFovY() {
      return fovY;
   }

   public double getDistance() {
      double area = this.getArea();
      SmartDashboard.putNumber("Limelight Distance", area);
      // System.out.println("Area" + area);
      // System.out.println("Distance in feet:" + distance);
      return area;
   }

   public boolean enableSecondaryCameraStream() {
      return stream.setNumber(2);
   }

   public double getDistanceBasedOnArea() {
      return HelperFunctions.mean(limelightbuffer.toArray());

   }

   @Override
   public void periodic() {
      limelightbuffer.addLast(getX());
   }

   public void updateDashboard() {
		SmartDashboard.putNumber("ta", getArea());
      SmartDashboard.putNumber("tx", getX());
      SmartDashboard.putNumber("ty", getY());
	}
}
