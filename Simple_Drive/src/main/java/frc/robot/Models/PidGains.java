package frc.robot.Models;

public class PidGains {
    public double P, I, D, F;
    public int slot, iZone;

    public PidGains(int slot, double p, double i, double d, double f, int iZone) {
        this.slot = slot;
        this.P = p;
        this.I = i;
        this.D = d;
        this.F = f;
        this.iZone = iZone;
    }

}
