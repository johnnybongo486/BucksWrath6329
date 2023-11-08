package frc.lib.models;

public interface IPositionControlledSubsystem {

	public double targetPosition = 0;
	public double onTargetThreshold = 0;

	public boolean setTargetPosition(int targetPosition);

	public double getTargetPosition();

	public double getCurrentPosition();

	public double getCurrentVelocity();

	public void motionMagicControl();

	public boolean isInPosition(int targetPosition);
}
