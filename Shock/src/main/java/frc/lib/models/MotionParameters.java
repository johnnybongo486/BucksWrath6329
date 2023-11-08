package frc.lib.models;

public class MotionParameters {
	private int _acceleration = 0;
	private int _cruiseVelocity = 0;
	private int _minVelocity = 0;
	private SRXGains _gains = new SRXGains(0, 0, 0, 0, 0, 0);

	public MotionParameters(int acceleration, int cruiseVelocity, SRXGains gains) {
		_acceleration = acceleration;
		_cruiseVelocity = cruiseVelocity;
		_gains = gains;
	}

	public MotionParameters(int acceleration, int cruiseVelocity, SRXGains gains, int minVelocity) {
		this(acceleration, cruiseVelocity, gains);
		_minVelocity = minVelocity;
	}

	public MotionParameters() {
	}

	public SRXGains getGains() {
		return _gains;
	}

	public int getAcceleration() {
		return _acceleration;
	}

	public int getCruiseVelocity() {
		return _cruiseVelocity;
	}

	public int getMinVelocity() {
		return _minVelocity;
	}

	public void setGains(SRXGains gains) {
		_gains = gains;
	}

	public void setAcceleration(int acceleration) {
		_acceleration = acceleration;
	}

	public void setCruiseVelocity(int cruiseVelocity) {
		_cruiseVelocity = cruiseVelocity;
	}

	public void setMinVelocity(int minVelocity) {
		_minVelocity = minVelocity;
	}

}
