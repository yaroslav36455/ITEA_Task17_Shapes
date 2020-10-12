package ua.itea.model;

public class Rectangle extends Shape {
	
	public Rectangle(Rectangle other) {
		super(other);
	}
	
	public Rectangle(double side, String color) throws InvalidArgumentsException {
		super(color);
		reset(side);
	}
	
	public Rectangle(double sideA, double sideB, String color) throws InvalidArgumentsException {
		super(color);
		reset(sideA, sideB);
	}
	
	@Override
	public String getName() {
		return "Rectangle";
	}
	
	public void reset(double side) throws InvalidArgumentsException {
		if(side <= 0.0) {
			throw new InvalidArgumentsException("Side less than or equal to 0.0 units");
		}
		
		reset(side, side);
	}
	
	public void reset(double sideA, double sideB) throws InvalidArgumentsException {
		if(sideA <= 0.0 || sideB <= 0) {
			throw new InvalidArgumentsException("Side less than or equal to 0.0 units");
		}
		
		setPerimeter(computePerimeter(sideA, sideB));
		setArea(computeArea(sideA, sideB));
	}
	
	private static double computePerimeter(double sideA, double sideB) {
		return (sideA + sideB) * 2;
	}
	
	private static double computeArea(double sideA, double sideB) {
		return sideA * sideB;
	}
}
