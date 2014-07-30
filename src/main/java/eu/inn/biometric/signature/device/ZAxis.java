package eu.inn.biometric.signature.device;


public class ZAxis {
	private int maximum;
	private int minimum;
	private boolean supported;
	
	ZAxis() {
	}
	
	public int getMaximum() {
		return maximum;
	}
	public int getMinimum() {
		return minimum;
	}
	public boolean isSupported() {
		return supported;
	}
	
	public void setSupported(boolean supported) {
		this.supported = supported;
	}
	
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
}
