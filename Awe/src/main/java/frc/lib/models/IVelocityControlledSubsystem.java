package frc.lib.models;

public interface IVelocityControlledSubsystem {

	public double targetVelocity = 0;
	public double onTargetThreshold = 0;

	public boolean setTargetVelocity(double targetVelocity);

	public double getTargetVelocity();

	public double getCurrentVelocity();

	public void velocityControl();

	public boolean isAtVelocity(double targetVelocity);
}
