package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class RobotMap {

   public static Solenoid shifterSol;
   public static Solenoid clawSol;
  
   public class PDP {

       public static final int LFtalon = 0;
       public static final int LBtalon = 1;
       public static final int RFtalon = 2;
       public static final int RBtalon = 3;

   }

   public static void init() {

       // Solenoids
       shifterSol = new Solenoid(0,0);
       clawSol = new Solenoid(0,1);
   }

}

