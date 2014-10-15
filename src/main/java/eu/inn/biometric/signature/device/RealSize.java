package eu.inn.biometric.signature.device;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * RealSize.java is part of BioSignIn project
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




public class RealSize {

	private Dimension dimension;

	public RealSize(MetricUnits unit, Dimension dimension) {
		this.unit = unit;
		this.dimension = dimension;
		this.width = dimension.getWidth();
		this.height = dimension.getHeight();
	}

	RealSize() {
	}

	private MetricUnits unit;
	private int width;
	private int height;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Dimension getDimension() {
		if (dimension == null)
			dimension = new Dimension(width, height);
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public MetricUnits getUnit() {
		return unit;
	}

	public void setUnit(MetricUnits unit) {
		this.unit = unit;
	}
}
