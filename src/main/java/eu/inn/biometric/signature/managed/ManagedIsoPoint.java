package eu.inn.biometric.signature.managed;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ManagedIsoPoint.java is part of BioSignIn project
 * %%
 * Copyright (C) 2014 Innovery SpA
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */


import java.awt.Point;

import eu.inn.biometric.signature.isoiec19794.y2007.Channel;
import eu.inn.biometric.signature.isoiec19794.y2007.IsoPoint;

public class ManagedIsoPoint {

	private long time = System.currentTimeMillis();
	private int pressure;
	private int x;
	private int y;

	public int getPressure() {
		return pressure;
	}

	public ManagedIsoPoint(int x, int y) {
		this(x, y, 0);
	}

	public ManagedIsoPoint(int x, int y, int pressure) {
		this.setX(x);
		this.setY(y);
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

}