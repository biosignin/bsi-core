package eu.inn.biometric.signature.device;

import java.awt.Dimension;
import java.util.List;

import eu.inn.biometric.signature.managed.ManagedIsoPoint;

public class CapturingComponent {

	private RealSize realSize = new RealSize();
	private SignArea signArea = new SignArea();
	private ZAxis zAxis = new ZAxis();
	private Pressure pressure = new Pressure();
	private TimeInfo timeInfo = new TimeInfo();
	private boolean azimuthSupported;
	private boolean rotationSupported;

	public static CapturingComponent fromIso(List<ManagedIsoPoint> packets) {
		SignArea area = SignArea.fromSignaturePackets(packets);
		CapturingComponent information = new CapturingComponent();
		short pressure = Short.MIN_VALUE;
		for (ManagedIsoPoint packet : packets) {
			if (packet.getPressure() > pressure) {
				pressure = new Integer(packet.getPressure()).shortValue();
			}
		}
		information.zAxis = new ZAxis();
		information.realSize = new RealSize(MetricUnits.Pixels, new Dimension(area.getWidth(),
				area.getHeight()));
		information.signArea = area;
		information.timeInfo = new TimeInfo();
		information.pressure = new Pressure();
		information.pressure.setPressureSupported(true);
		information.pressure.setAirmovesSupported(false);
		information.pressure.setMinimum(0);
		information.pressure.setMaximum(pressure);
		return information;
	}

	public CapturingComponent() {
		zAxis = new ZAxis();
		timeInfo = new TimeInfo();
		pressure = new Pressure();
		pressure.setPressureSupported(true);
		pressure.setAirmovesSupported(false);
		pressure.setMinimum(0);
	}

	public Pressure getPressure() {
		return pressure;
	}

	public SignArea getSignArea() {
		return signArea;
	}

	public RealSize getRealSize() {
		return realSize;
	}

	public void setSignArea(SignArea signArea) {
		this.signArea = signArea;
	}

	public ZAxis getZAxis() {
		return zAxis;
	}

	public TimeInfo getTimeInfo() {
		return timeInfo;
	}

	public void setRealSize(RealSize realSize) {
		this.realSize = realSize;
	}

	public boolean isAzimuthSupported() {
		return azimuthSupported;
	}

	public void setAzimuthSupported(boolean azimuthSupported) {
		this.azimuthSupported = azimuthSupported;
	}

	public boolean isRotationSupported() {
		return rotationSupported;
	}

	public void setRotationSupported(boolean rotationSupported) {
		this.rotationSupported = rotationSupported;
	}

}
