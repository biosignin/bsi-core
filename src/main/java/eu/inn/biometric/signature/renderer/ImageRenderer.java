package eu.inn.biometric.signature.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.List;

import eu.inn.biometric.signature.managed.ManagedIsoPoint;

public class ImageRenderer {

	public static void drawPoints(List<? extends ManagedIsoPoint> points, Graphics2D graphics,
			Color penColor, Point offset, float maxTickness, float maxPressure) {
		drawPoints(points, graphics, penColor, offset, maxTickness, 0.5f, maxPressure);
	}

	public static void drawPoints(List<? extends ManagedIsoPoint> points, Graphics2D graphics,
			Color penColor, Point offset, float maxTickness, float minTickness, float maxPressure) {
		if (maxTickness - minTickness <= 0)
			throw new IllegalArgumentException(String.format(
					"maxTickness [%s] must be greater then minTickness [%s]", maxTickness, minTickness));
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(penColor);
		float strokeRange = maxTickness - minTickness;
		for (int i = 1; i < points.size(); i++) {
			ManagedIsoPoint localPenPoint1 = points.get(i);
			ManagedIsoPoint localPenPoint2 = points.get(i - 1);
			if ((localPenPoint1.pressure > 0) && (localPenPoint2.pressure > 0)) {
				graphics.setStroke(new BasicStroke((strokeRange * localPenPoint1.pressure / maxPressure)
						+ minTickness));
				double j = localPenPoint2.getX() - offset.getX();
				double k = localPenPoint2.getY() - offset.getY();
				double m = localPenPoint1.getX() - offset.getX();
				double n = localPenPoint1.getY() - offset.getY();
				Shape l = new Line2D.Double(j, k, m, n);
				graphics.draw(l);
			}
		}
	}
}
