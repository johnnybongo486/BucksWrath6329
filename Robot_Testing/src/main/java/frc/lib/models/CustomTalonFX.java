package frc.lib.models;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

public class CustomTalonFX extends TalonFX {
    private int defaultPidIndex = 0;

    public CustomTalonFX(int deviceNumber) {
        super(deviceNumber);

        //TODO: I haven't found where these can be set in v6
        /*this.configNominalOutputForward(0.0);
        this.configNominalOutputReverse(0.0);
        this.configPeakOutputForward(1);
        this.configPeakOutputReverse(-1);
        this.configMotionProfileTrajectoryPeriod(0);*/
    }

    public int getDefaultPidIndex() {
        return this.defaultPidIndex;
    }

    public void setDefaultPidIndex(int pidIndex) {
        this.defaultPidIndex = pidIndex;
    }

    // ------------- HELPER METHODS -------- //

    public StatusCode configPIDF(int slotIdx, double P, double I, double D, double F) {

        SlotConfigs slotConfigs = new SlotConfigs();
        slotConfigs.SlotNumber = slotIdx;
        slotConfigs.withKP(P).withKI(I).withKD(D).withKV(F);
        
        return this.getConfigurator().apply(slotConfigs);
    }

    public StatusCode configPIDF(int slotIdx, double P, double I, double D, double F, int iZone) {
        //StatusCode eCode = this.configPIDF(slotIdx, P, I, D, F);
        StatusCode eCode = this.configPIDF(slotIdx, P, I, D, F, iZone); //fixed
        //TODO: I haven't been able to find an equivalent for config_IntegralZone
        // We may need to come back to this
        //eCode = this.config_IntegralZone(slotIdx, iZone);
        return eCode;
    }

    public StatusCode configPIDF(SRXGains gains) {
        return this.configPIDF(gains.slot, gains.P, gains.I, gains.D, gains.F, gains.iZone);
    }

    public void configMotionParameters(MotionParameters parameters) {
        MotionMagicConfigs mMagicConfigs = new MotionMagicConfigs()
            .withMotionMagicAcceleration(parameters.getAcceleration())
            .withMotionMagicCruiseVelocity(parameters.getCruiseVelocity());
        this.getConfigurator().apply(mMagicConfigs,.03);
        this.setGains(parameters.getGains());
    }

    public void selectMotionParameters(MotionParameters parameters) {
        //TODO: it looks like MotionMagicConfigs does not support "slot".
        // I wonder if this is not necessary?
        // this.selectProfileSlot(parameters.getGains().slot);

        MotionMagicConfigs mMagicConfigs = new MotionMagicConfigs()
            .withMotionMagicAcceleration(parameters.getAcceleration())
            .withMotionMagicCruiseVelocity(parameters.getCruiseVelocity());
        
		this.getConfigurator().apply(mMagicConfigs, .03);
    }

    public StatusCode setGains(SRXGains gains) {
        return this.configPIDF(gains.slot, gains.P, gains.I, gains.D, gains.F, gains.iZone);
    }

    /*public void selectProfileSlot(int slotIdx) {
        super.selectProfileSlot(slotIdx, defaultPidIndex);
    }*/
}
