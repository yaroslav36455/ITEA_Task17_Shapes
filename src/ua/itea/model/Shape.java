package ua.itea.model;

public abstract class Shape {
	private double perim;
	private double area;
	private String color;
	
	protected Shape(Shape other) {
		this.perim = other.perim;
		this.area = other.area;
		this.color = other.color;
	}
	
	protected Shape(String color) {
		this.color = color;
	}
	
	public abstract String getName();
	
	public final double getPerimeter() {
		return perim;
	}
	
	public final double getArea() {
		return area;
	}
	
	public final String getColor() {
		return color;
	}
	
	protected final void setPerimeter(double perim) {
		this.perim = perim;
	}
	
	protected final void setArea(double area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "Shape [getName()=" + getName() + ", getPerimeter()=" + getPerimeter()
				+ ", getArea()=" + getArea() + ", getColor()=" + getColor() + "]";
	}
}
