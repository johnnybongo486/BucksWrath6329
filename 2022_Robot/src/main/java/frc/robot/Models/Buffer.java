package frc.robot.Models;


public class Buffer extends edu.wpi.first.util.CircularBuffer {

	int size = 0;

	public Buffer(int size) {
		super(size);
		this.size = size;
	}

	public double[] toArray() {
		double[] arr = new double[this.size];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = this.get(i);
		}

		return arr;
	}

}
