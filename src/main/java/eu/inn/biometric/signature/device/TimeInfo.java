package eu.inn.biometric.signature.device;


public class TimeInfo {
	private boolean fixedSamplingRate;
	private int samplingRatePointsPerSecond;
	private boolean supported;
	private boolean timeSupportDuringAirMoves;
	
	TimeInfo() {
	}
	
	public boolean isFixedSamplingRate() {
		return fixedSamplingRate;
	}
	public int getSamplingRatePointsPerSecond() {
		return samplingRatePointsPerSecond;
	}
	public boolean isSupported() {
		return supported;
	}
	public boolean isTimeSupportDuringAirMoves() {
		return timeSupportDuringAirMoves;
	}
	public void setFixedSamplingRate(boolean fixedSamplingRate) {
		this.fixedSamplingRate = fixedSamplingRate;
	}
	public void setSamplingRatePointsPerSecond(int samplingRatePointsPerSecond) {
		this.samplingRatePointsPerSecond = samplingRatePointsPerSecond;
	}
	public void setSupported(boolean supported) {
		this.supported = supported;
	}
	public void setTimeSupportDuringAirMoves(boolean timeSupportDuringAirMoves) {
		this.timeSupportDuringAirMoves = timeSupportDuringAirMoves;
	}

}
