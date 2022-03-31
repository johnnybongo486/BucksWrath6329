package frc.robot.Models;
/**
 * A drivetrain command consisting of the left, right motor settings and whether
 * the brake mode is enabled.
 */
public class DriveSignal {
	protected double mLeftMotor;
	protected double mLeftFollowMotor;
	protected double mleftTopFollowMotor;
	protected double mRightMotor;
	protected double mRightFollowMotor;
	protected double mRightTopFollowMotor;
	protected boolean mBrakeMode;

	public DriveSignal(double left, double right) {
		this(left, right, true);
	}

	public DriveSignal(double left, double right, boolean brakeMode) {
		
		if (left > 0) {
			mLeftFollowMotor = left*0.91;
			mLeftMotor = left*0.86;
			mleftTopFollowMotor = left;
		}
		if (left < 0) {
			mLeftFollowMotor = left*0.93;
			mLeftMotor = left*0.88;
			mleftTopFollowMotor = left;
		}

		if (right > 0) {
			mRightFollowMotor = right*0.92;
			mRightMotor = right*0.92;
			mRightTopFollowMotor = right;
		}

		if (right < 0) {
			mRightFollowMotor = right*0.92;
			mRightMotor = right*0.92;
			mRightTopFollowMotor = right;
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

	public double getRightTopFollow() {
		return mRightTopFollowMotor;
	}

	public double getLeftFollow(){
		return mLeftFollowMotor;
	}

	public double getLeftTopFollow(){
		return mleftTopFollowMotor;
	}

	public boolean getBrakeMode() {
		return mBrakeMode;
	}

	@Override
	public String toString() {
		return "L: " + mLeftMotor + ", R: " + mRightMotor + (mBrakeMode ? ", BRAKE" : "");
	}
}
