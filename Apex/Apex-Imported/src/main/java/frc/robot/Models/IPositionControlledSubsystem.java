package frc.robot.Models;

public interface IPositionControlledSubsystem {

	public int targetPosition = 0;
	public int onTargetThreshold = 0;

	public boolean setTargetPosition(int targetPosition);

	public double getTargetPosition();

	public double getCurrentPosition();

	public double getCurrentVelocity();

	public void motionMagicControl();

	public boolean isInPosition(int targetPosition);
}