package eu.inn.biometric.signature.device;

import java.awt.Dimension;

public class RealSize {

	private Dimension dimension;

	public RealSize(MetricUnits unit, Dimension dimension) {
		this.unit = unit;
		this.dimension = dimension;
		this.width = dimension.width;
		this.height = dimension.height;
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
