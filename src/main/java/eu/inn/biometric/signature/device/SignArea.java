package eu.inn.biometric.signature.device;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

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
		return location.y + size.height;
	}

	public int getHeight() {
		return size.height;
	}

	public int getWidth() {
		return size.width;
	}


	private int _bottom;

	public int getBottom() {
		return location.y;
	}

	public void setBottom(int value) {
		_bottom = value;
		calculate();
	}

	private int _left;

	public int getLeft() {
		return location.x;
	}

	public void setLeft(int value) {
		_left = value;
		calculate();
	}

	private void calculate() {
		size.height = _top - location.y;
		size.width = _right - location.x;
		location.y = _bottom;
		location.x = _left;

	}

	private int _right;

	public int getRight() {
		return location.x + size.width;
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
