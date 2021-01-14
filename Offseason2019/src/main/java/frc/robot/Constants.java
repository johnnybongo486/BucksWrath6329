package frc.robot;

public class Constants {

    /** which Talon on CANBus */
    public static final int kTalonID = 0;
    public final static int REMOTE_0 = 0;
    public final static int REMOTE_1 = 1;
    public final static int PID_PRIMARY = 0;
    public final static int PID_TURN = 1;
    public final static int SLOT_0 = 0;
    public final static int SLOT_1 = 1;
    public final static int SLOT_2 = 2;
    public final static int SLOT_3 = 3;
    public final static double kTurnTravelUnitsPerRotation = 3600;

    /**
 	* Empirically measure what the difference between encoders per 360'
 	* Drive the robot in clockwise rotations and measure the units per rotation.
 	* Drive the robot in counter clockwise rotations and measure the units per rotation.
 	* Take the average of the two.
 	*/
    public final static int kEncoderUnitsPerRotation = 35000; //17000

    /**
 	* How many sensor units per rotation. Using CTRE Magnetic Encoder.
 	*
 	* @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
 	*/
    public static final double kSensorUnitsPerRotation = 4096;

    /**
 	* Which PID slot to pull gains from. Starting 2018, you can choose from
 	* 0,1,2 or 3. Only the first two (0,1) are visible in web-based
 	* configuration.
 	*/
    public static final int kSlotIdx = 0;

    /**
 	* Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
 	* now we just want the primary one.
 	*/
    public static final int kPIDLoopIdx = 0;
    /**
 	* set to zero to skip waiting for confirmation, set to nonzero to wait and
 	* report to DS if action fails.
 	*/
    public static final int kTimeoutMs = 30;

    /**
 	* Base trajectory period to add to each individual trajectory point's
 	* unique duration. This can be set to any value within [0,255]ms.
 	*/
    public static final int kBaseTrajPeriodMs = 0;

    /**
 	* Motor deadband, set to 1%.
 	*/
    public static final double kNeutralDeadband = 0.01;
}

