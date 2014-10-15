package eu.inn.biometric.signature.device;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * SignArea.java is part of BioSignIn project
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


import java.util.List;

import eu.inn.biometric.signature.managed.ManagedIsoPoint;

public class SignArea {

	public static SignArea fromSignaturePackets(
			List<ManagedIsoPoint> packets) {
		Boolean flag = false;
		Double minX = Double.MAX_VALUE;
		Double minY = Double.MAX_VALUE;
		Double maxX = Double.MIN_VALUE;
		Double maxY = Double.MIN_VALUE;
		for (ManagedIsoPoint packet : packets) {
			if (packet.getPressure() > 0) {
				flag = true;
				minX = Math.min(minX, packet.getX());
				maxX = Math.max(maxX, packet.getX());
				maxY = Math.max(maxY, packet.getY());
				minY = Math.min(minY, packet.getY());
			}
		}
		Double width = maxX - minX;
		Double heigth = maxY - minY;
		if (flag) {
			width = Math.max(1, width);
			heigth = Math.max(1, heigth);
		}
		return new SignArea(new Point(minX.intValue(), minY.intValue()), new Dimension(width.intValue(),
				heigth.intValue()));
	}

	SignArea() {
		location = new Point();
		size = new Dimension();
	}

	private Point location;
	private Dimension size;

	public void setDimension(Dimension size){
		this.size = size;
		
	}
	
	public Dimension getDimension(){
		return size;
		
	}
	
	
	private int _top;

	public int getTop() {
		return location.getY() + size.getHeight();
	}

	public int getHeight() {
		return size.getHeight();
	}

	public int getWidth() {
		return size.getWidth();
	}


	private int _bottom;

	public int getBottom() {
		return location.getY();
	}

	public void setBottom(int value) {
		_bottom = value;
		calculate();
	}

	private int _left;

	public int getLeft() {
		return location.getX();
	}

	public void setLeft(int value) {
		_left = value;
		calculate();
	}

	private void calculate() {
		size.setHeight(_top - location.getY());
		size.setWidth(_right - location.getX());
		location.setY(_bottom);
		location.setX(_left);

	}

	private int _right;

	public int getRight() {
		return location.getX() + size.getWidth();
	}

	public void setRight(int value) {
		_right = value;
		calculate();
	}

	public SignArea(Point location, Dimension size) {
		this.location = location;
		this.size = size;
	}

	public Point getLocation() {
		return location;
	}

	public Dimension getSize() {
		return size;
	}

}
