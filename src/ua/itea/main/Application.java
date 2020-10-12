package ua.itea.main;

import ua.itea.db.DBConnector;
import ua.itea.db.ShapeDB;
import ua.itea.gui.Window;
import ua.itea.model.ShapeFactory;
import ua.itea.service.ShapeStorageService;

public class Application {

	public static void main(String[] args) {
		DBConnector conn = new DBConnector();
		ShapeDB database = new ShapeDB();
		ShapeStorageService sss = new ShapeStorageService(conn, database);
		
		ShapeFactory shapeFactory = new ShapeFactory();
		
		new Window(shapeFactory, sss);
	}

}
