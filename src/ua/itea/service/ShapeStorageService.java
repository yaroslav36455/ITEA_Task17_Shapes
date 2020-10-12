package ua.itea.service;

import java.sql.SQLException;

import ua.itea.db.DBConnector;
import ua.itea.db.EmptyTableException;
import ua.itea.db.ShapeDB;
import ua.itea.model.Shape;

public class ShapeStorageService {
	private DBConnector conn;
	private ShapeDB shapeDB;
	
	public ShapeStorageService(DBConnector conn, ShapeDB shapeDB) {
		this.conn = conn;
		this.shapeDB = shapeDB;
	}
	
	public String[] getTableHeader() {
		return shapeDB.getTableHeader();
	}

	public String[][] loadTable() throws SQLException, EmptyTableException {
		return shapeDB.getShapes(conn.getConnection());
	}
	
	public void save(Shape shape) throws SQLException {
		shapeDB.addShape(conn.getConnection(), shape);
	}
}
