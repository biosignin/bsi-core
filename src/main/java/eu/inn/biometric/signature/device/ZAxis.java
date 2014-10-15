package eu.inn.biometric.signature.device;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ZAxis.java is part of BioSignIn project
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
