package ua.itea.model;

public class ShapeFactory {
	private ColorFactory colorFactory;
	
	public ShapeFactory() {
		colorFactory = new ColorFactory();
	}
	
	public Circle createCircle(double radius) throws InvalidArgumentsException {
		Color newColor = colorFactory.createColor();
		
		return new Circle(radius, newColor.toString());
	}
	
	public Rectangle createRectangle(double sideA, double sideB) throws InvalidArgumentsException {
		Color newColor = colorFactory.createColor();
		
		return new Rectangle(sideA, sideB, newColor.toString());
	}
	
	public Triangle createTriangleBySideSideAngle(double sideA,
												  double sideB,
												  double angleAB) throws InvalidArgumentsException {
		Color newColor = colorFactory.createColor();
		Angle angle = new Angle();
		
		angle.setDegree(angleAB);
		return new Triangle(sideA, sideB, angle, newColor.toString());
	}
	
	public Triangle createTriangleBySideAngleAngle(double sideA,
												   double angleAB,
												   double angleAC) throws InvalidArgumentsException {
		Color newColor = colorFactory.createColor();
		Angle angleFirst = new Angle();
		Angle angleSecond = new Angle();

		angleFirst.setDegree(angleAB);
		angleSecond.setDegree(angleAC);
		
		return new Triangle(sideA, angleFirst, angleSecond, newColor.toString());
}
}
