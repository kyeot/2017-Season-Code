package org.usfirst.frc.team2783.robot.vision;

public class Contour {
	
	private double area;
	private double centerX;
	private double centerY;
	private double width;
	private double height;
	private double solidity;
	
	public Contour(double area, double centerX, double centerY, double width, double height, double solidity){
		this.area = area;
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		this.solidity = solidity;
	}
	
	public double getArea() {
		return area;
	}
	
	public void setArea(double area) {
		this.area = area;
	}
	
	public double getCenterX() {
		return centerX;
	}
	
	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}
	
	public double getCenterY() {
		return centerY;
	}
	
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getSolidity() {
		return solidity;
	}
	
	public void setSolidity(double solidity) {
		this.solidity = solidity;
	}
}
