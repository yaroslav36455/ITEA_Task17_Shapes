package ua.itea.model;

public class Circle extends Shape {
	
	public Circle(Circle other) {
		super(other);
	}
	
	public Circle(double radius, String color) throws InvalidArgumentsException{
		super(color);
		reset(radius);
	}
	
	@Override
	public String getName() {
		return "Circle";
	}
	
	public void reset(double radius) throws InvalidArgumentsException  {
		if(radius <= 0.0) {
			throw new InvalidArgumentsException("Ridius less than or equal to 0.0 units");
		}
		
		setPerimeter(computePerimeter(radius));
		setArea(computeArea(radius));
	}
	
	private static double computePerimeter(double radius) {
		return radius * 2.0 * Math.PI;
	}
	
	private static double computeArea(double radius) {
		return radius * radius * Math.PI;
	}
}
