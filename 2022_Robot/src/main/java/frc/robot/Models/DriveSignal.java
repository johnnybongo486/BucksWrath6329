package frc.robot.Models;
/**
 * A drivetrain command consisting of the left, right motor settings and whether
 * the brake mode is enabled.
 */
public class DriveSignal {
	protected double mLeftMotor;
	protected double mLeftFollowMotor;
	protected double mRightMotor;
	protected double mRightFollowMotor;
	protected boolean mBrakeMode;

	public DriveSignal(double left, double right) {
		this(left, right, true);
	}

	public DriveSignal(double left, double right, boolean brakeMode) {
		
		if (left > 0) {
			mLeftFollowMotor = left;
			mLeftMotor = left*0.95;
		}
		if (left < 0) {
			mLeftFollowMotor = left;
			mLeftMotor = left*0.97;
		}

		if (right > 0) {
			mRightFollowMotor = right*0.97;
			mRightMotor = right;
		}

		if (right < 0) {
			mRightFollowMotor = right*0.95;
			mRightMotor = right;
		}
		
		mBrakeMode = brakeMode;
	}

	public static DriveSignal NEUTRAL = new DriveSignal(0, 0);
	public static DriveSignal BRAKE = new DriveSignal(0, 0, true);

	public double getLeft() {
		return mLeftMotor;
	}

	public double getRight() {
		return mRightMotor;
	}

	public double getRightFollow() {
		return mRightFollowMotor;
	}

	public double getLeftFollow(){
		return mLeftFollowMotor;
	}

	public boolean getBrakeMode() {
		return mBrakeMode;
	}

	@Override
	public String toString() {
		return "L: " + mLeftMotor + ", R: " + mRightMotor + (mBrakeMode ? ", BRAKE" : "");
	}
}
