package ua.itea.model;

public class ColorFactory {
	
	public Color createColor() {
		Color[] colors = Color.values();
		
		return colors[(int) (Math.random() * colors.length)];
	}
}
