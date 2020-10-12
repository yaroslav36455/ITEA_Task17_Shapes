package ua.itea.model;

public class Triangle extends Shape {
	
	public Triangle(Triangle other) {
		super(other);
	}
	
	public Triangle(double sideA, double sideB,
			        Angle angleAB, String color) throws InvalidArgumentsException {
		super(color);
		reset(sideA, sideB, angleAB);
	}
	
	public Triangle(double sideA, Angle angleAB,
				    Angle angleAC, String color) throws InvalidArgumentsException {
		super(color);
		reset(sideA, angleAB, angleAC);
	}
	
	@Override
	public String getName() {
		return "Triangle";
	}
	
	public void reset(double sideA, double sideB,
					  Angle angleAB) throws InvalidArgumentsException {
		if (sideA <= 0.0 || sideB <= 0.0) {
			throw new InvalidArgumentsException("Side less than or equal to 0.0 units");
		} else if (angleAB.getDegree() >= 180.0) {
			throw new InvalidArgumentsException("Angle greater than or equal to 180.0 degrees");
		}
		
		setPerimeter(computePerimeter(sideA, sideB, angleAB));
		setArea(computeArea(sideA, sideB, angleAB));
	}
	
	public void reset(double sideA,
			          Angle angleAB, Angle angleAC) throws InvalidArgumentsException {
		if (sideA <= 0.0) {
			throw new InvalidArgumentsException("Side less than or equal to 0.0 units");
		} else if ((angleAB.getDegree() + angleAC.getDegree()) >= 180.0) {
			throw new InvalidArgumentsException("The sum of the angles is greater"
									   + " than or equal to 180.0 degrees");
		}
		
		setPerimeter(computePerimeter(sideA, angleAB, angleAC));
		setArea(computeArea(sideA, angleAB, angleAC));
	}
	
	private static double computePerimeter(double sideA, double sideB, Angle angleAB) {
		double sideC = Math.sqrt(sideA * sideA + sideB * sideB
								 - 2 * sideA * sideB * Math.cos(angleAB.getRadians()));
		return sideA + sideB + sideC;
	}
	
	private static double computeArea(double sideA, double sideB, Angle angleAB) {
		return sideA * sideB * Math.sin(angleAB.getRadians()) / 2;
	}
	
	private static double computePerimeter(double sideA, Angle angleAB, Angle angleAC) {
		double heightA = sideA / (angleAB.ctg() + angleAC.ctg());
		double sideAPartOne = heightA / angleAB.tan();
		double sideAPartTwo = heightA / angleAC.tan();
		double sideB = Math.sqrt(sideAPartOne * sideAPartOne + heightA * heightA);
		double sideC = Math.sqrt(sideAPartTwo * sideAPartTwo + heightA * heightA);
		
		return sideA + sideB + sideC;
	}
	
	private static double computeArea(double sideA, Angle angleAB, Angle angleAC) {
		return sideA * sideA / (angleAB.ctg() + angleAC.ctg()) / 2;
	}
}
