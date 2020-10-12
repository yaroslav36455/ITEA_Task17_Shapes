package ua.itea.model;

public enum Color {
	BLACK("Black"), WHITE("White"), GREY("Grey"), RED("Red"), GREEN("Green"),
	BLUE("Blue"), YELLOW("Yellow"), PINK("Pink"), BROWN("Brown"), ORANGE("Orange");
	
	private String color;
	
	private Color(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return color;
	}
}
