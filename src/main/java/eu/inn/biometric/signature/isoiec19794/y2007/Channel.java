package eu.inn.biometric.signature.isoiec19794.y2007;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * Channel.java is part of BioSignIn project
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


public enum Channel {

	R("Rotation", 0, Short.MAX_VALUE - Short.MIN_VALUE), EL("Elevation", 0, Short.MAX_VALUE - Short.MIN_VALUE), AZ("Azimuth", 0, Short.MAX_VALUE
			- Short.MIN_VALUE), TY("TiltY", Short.MIN_VALUE, Short.MAX_VALUE), TX("TiltX", Short.MIN_VALUE, Short.MAX_VALUE), S("Tip Switch", 0, 1), F("Pressure", 0, Short.MAX_VALUE
			- Short.MIN_VALUE), DT("Delta Time", 0, Short.MAX_VALUE - Short.MIN_VALUE), T("Time", 0, Short.MAX_VALUE
			- Short.MIN_VALUE), AY("AccelerationY", Short.MIN_VALUE, Short.MAX_VALUE), AX("AccelerationX", Short.MIN_VALUE, Short.MAX_VALUE), VY("VelocityY", Short.MIN_VALUE, Short.MAX_VALUE), VX("VelocityX", Short.MIN_VALUE, Short.MAX_VALUE), Z("CoordinateZ", 0, Short.MAX_VALUE
			- Short.MIN_VALUE), Y("CoordinateY", Short.MIN_VALUE, Short.MAX_VALUE), X("CoordinateX", Short.MIN_VALUE, Short.MAX_VALUE);

	private Channel(String description, Integer minValue, Integer maxValue) {
		this.description = description;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	private Channel(String description, Short minValue, Short maxValue) {
		this(description, minValue.intValue(), maxValue.intValue());
	}

	private String description;

	public String getDescription() {
		return description;
	}

	private int minValue;
	private int maxValue;

	public static Channel fromInteger(int x) {
		switch (x) {
		case 0:
			return R;
		case 1:
			return EL;
		case 2:
			return AZ;
		case 3:
			return TY;
		case 4:
			return TX;
		case 5:
			return S;
		case 6:
			return F;
		case 7:
			return DT;
		case 8:
			return T;
		case 9:
			return AY;
		case 10:
			return AX;
		case 11:
			return VY;
		case 12:
			return VX;
		case 13:
			return Z;
		case 14:
			return Y;
		case 15:
			return X;
		}
		return null;
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}
}
