package eu.inn.biometric.signature.device;

public class Pressure {
	private int maximum;
	private int minimum;
	private boolean pressureSupported;
	private boolean airmovesSupported;

	Pressure() {
	}

	public int getMaximum() {
		return maximum;
	}

	public int getMinimum() {
		return minimum;
	}

	public boolean isPressureSupported() {
		return pressureSupported;
	}

	public boolean isAirmovesSupported() {
		return airmovesSupported;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public void setPressureSupported(boolean pressureSupported) {
		this.pressureSupported = pressureSupported;
	}

	public void setAirmovesSupported(boolean airmovesSupported) {
		this.airmovesSupported = airmovesSupported;
	}
}
