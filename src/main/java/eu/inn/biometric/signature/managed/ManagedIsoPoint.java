package eu.inn.biometric.signature.managed;

import java.awt.Point;

import eu.inn.biometric.signature.isoiec19794.y2007.Channel;
import eu.inn.biometric.signature.isoiec19794.y2007.IsoPoint;

public class ManagedIsoPoint extends Point {

	private long time = System.currentTimeMillis();
	public int pressure;

	public int getPressure() {
		return pressure;
	}

	public ManagedIsoPoint(int x, int y) {
		this(x, y, 0);
	}

	public ManagedIsoPoint(int x, int y, int pressure) {
		super(x, y);
		this.pressure = pressure;
	}

	public ManagedIsoPoint(Point p, int pressure) {
		this(p.x, p.y, pressure);
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public static ManagedIsoPoint fromIsoPoint(IsoPoint p) {
		int time = p.getProperty(Channel.T);
		int x = p.getProperty(Channel.X);
		int y = p.getProperty(Channel.Y);
		int pressure = p.getProperty(Channel.F);
		ManagedIsoPoint ret = new ManagedIsoPoint(x, y, pressure);
		ret.setTime(time);
		return ret;
	}

	public void setX(java.lang.Double x) {

		this.x = x.intValue();
	}

	public void setY(java.lang.Double y) {
		this.y = y.intValue();
	}

}