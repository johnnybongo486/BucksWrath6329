package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public  class Constants {
    public static final class Autonomous {
        public static double kMaxSpeedMetersPerSecond = 3;
        public static double kMaxAccelerationMetersPerSecondSquared = 3;
        public static DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(Units.inchesToMeters(26)); // convert inches to meters AND needs to be kinem
        public static double kPThetaController = 0; // error?
        public static int kThetaControllerConstraints = 0; // error?
        public static double kPXController = 0;
        public static double kPYController = 0;
        public static final double ksVolts = 0.68;
        public static final double kvVoltSecondsPerMeter = 2.23;
        public static final double kaVoltSecondsSquaredPerMeter = 0.2;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
        public static final double kPDriveVel = 2.3;
        public static final double kDifferentialDriveKinematicsConstraint = 0.3;
        public static final double gearRatio = 16.36;  // 7.95 or 16.36
        public static final double wheelCircumferenceInches = 12.56;
        public static final double ENCODER_EDGES_PER_REV = 2048;
    }
}
