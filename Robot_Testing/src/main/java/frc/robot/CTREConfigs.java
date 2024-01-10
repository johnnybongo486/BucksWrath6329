package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

public final class CTREConfigs {
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANcoderConfiguration swerveCanCoderConfig;

    public CTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANcoderConfiguration();

        /* Swerve Angle Motor Configurations */
        CurrentLimitsConfigs angleSupplyLimit = new CurrentLimitsConfigs()
            .withSupplyCurrentLimitEnable(Constants.Swerve.angleEnableCurrentLimit)
            .withSupplyCurrentLimit(Constants.Swerve.angleContinuousCurrentLimit)
            .withSupplyCurrentThreshold(Constants.Swerve.anglePeakCurrentLimit)
            .withSupplyTimeThreshold(Constants.Swerve.anglePeakCurrentDuration);

        swerveAngleFXConfig.Slot0.kP = Constants.Swerve.angleKP;
        swerveAngleFXConfig.Slot0.kI = Constants.Swerve.angleKI;
        swerveAngleFXConfig.Slot0.kD = Constants.Swerve.angleKD;
        swerveAngleFXConfig.Slot0.kV = Constants.Swerve.angleKF;
        swerveAngleFXConfig.CurrentLimits = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        CurrentLimitsConfigs driveSupplyLimit = new CurrentLimitsConfigs()
            .withSupplyCurrentLimitEnable(Constants.Swerve.driveEnableCurrentLimit)
            .withSupplyCurrentLimit(Constants.Swerve.driveContinuousCurrentLimit)
            .withSupplyCurrentThreshold(Constants.Swerve.drivePeakCurrentLimit)
            .withSupplyTimeThreshold(Constants.Swerve.drivePeakCurrentDuration);

        swerveDriveFXConfig.Slot0.kP = Constants.Swerve.driveKP;
        swerveDriveFXConfig.Slot0.kI = Constants.Swerve.driveKI;
        swerveDriveFXConfig.Slot0.kD = Constants.Swerve.driveKD;
        swerveDriveFXConfig.Slot0.kV = Constants.Swerve.driveKF;        
        swerveDriveFXConfig.CurrentLimits = driveSupplyLimit;
        swerveDriveFXConfig.OpenLoopRamps = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(Constants.Swerve.openLoopRamp)
            .withTorqueOpenLoopRampPeriod(Constants.Swerve.openLoopRamp)
            .withVoltageOpenLoopRampPeriod(Constants.Swerve.openLoopRamp);
        swerveDriveFXConfig.ClosedLoopRamps = new ClosedLoopRampsConfigs()
            .withDutyCycleClosedLoopRampPeriod(Constants.Swerve.closedLoopRamp)
            .withTorqueClosedLoopRampPeriod(Constants.Swerve.closedLoopRamp)
            .withVoltageClosedLoopRampPeriod(Constants.Swerve.closedLoopRamp);
        
        /* Swerve CANCoder Configuration */
        // TODO: These configurations do not seem to be present with the v6 cancoderconfiguration class
        /*swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = Constants.Swerve.canCoderInvert;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;*/
    }
}
