package eu.inn.biometric.signature.renderer;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ImageRenderer.java is part of BioSignIn project
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


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.List;

import eu.inn.biometric.signature.managed.ManagedIsoPoint;

public class ImageRenderer {

	public static void drawPoints(Double ratio, List<? extends ManagedIsoPoint> points, Graphics2D graphics,
			Color penColor, Point offset, float maxTickness, float maxPressure) {
		drawPoints(ratio,points, graphics, penColor, offset, maxTickness, 0.5f, maxPressure);
	}

	public static void drawPoints(Double ratio, List<? extends ManagedIsoPoint> points, Graphics2D graphics,
			Color penColor, Point offset, float maxTickness, float minTickness, float maxPressure) {
		
		if (maxTickness - minTickness <= 0)
			throw new IllegalArgumentException(String.format(
					"maxTickness [%s] must be greater then minTickness [%s]", maxTickness, minTickness));
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(penColor);		
//		graphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		float strokeRange = maxTickness - minTickness;
		for (int i = 1; i < points.size(); i++) {
			ManagedIsoPoint localPenPoint1 = points.get(i);
			ManagedIsoPoint localPenPoint2 = points.get(i - 1);
			if ((localPenPoint1.getPressure() > 0) && (localPenPoint2.getPressure() > 0)) {
				graphics.setStroke(new BasicStroke((strokeRange * localPenPoint1.getPressure() / maxPressure)
						+ minTickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//				graphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				double x1 = localPenPoint2.getX()*ratio - offset.getX();
				double y1 = localPenPoint2.getY()*ratio - offset.getY();
				double x2 = localPenPoint1.getX()*ratio - offset.getX();
				double y2 = localPenPoint1.getY()*ratio - offset.getY();
//				if (i==points.size()-1 || points.get(i+1).getPressure()==0){
				if (true){
					Shape l = new Line2D.Double(x1, y1, x2, y2);
					graphics.draw(l);
//					graphics.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
				}
				else {
					System.err.println("SPLINE");
					ManagedIsoPoint localPenPoint3 = points.get(i+1);
					double x3 = localPenPoint3.getX()*ratio - offset.getX();
					double y3 = localPenPoint3.getY()*ratio - offset.getY();
					double 			cx1a = x1 + (x2 - x1) / 3;
					double 			cy1a = y1 + (y2 - y1) / 3;
					double 			cx1b = x2 - (x3 - x1) / 3;
					double 			cy1b = y2 - (y3 - y1) / 3;
					double 			cx2a = x2 + (x3 - x1) / 3;
					double 		cy2a = y2 + (y3 - y1) / 3;
					double 			cx2b = x3 - (x3 - x2) / 3;
					double 		cy2b = y3 - (y3 - y2) / 3;
					Path2D.Double path1 = new Path2D.Double();
					path1.moveTo(x1, y1);
					path1.curveTo(cx1a, cy1a, cx1b, cy1b, x2, y2);
					path1.curveTo(cx2a, cy2a, cx2b, cy2b, x3, y3);
					graphics.draw(path1);					
				}
					
//				Path2D.Double path = new Path2D.Double();
//				path.moveTo(j, k);
//				path.curveTo(cx1a, cy1a, cx1b, cy1b, x2, y2);
//				Shape l = new Line2D.Double(j, k, m, n);
//				graphics.draw(l);
			}
		}
	}
}
