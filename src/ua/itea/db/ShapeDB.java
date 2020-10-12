package ua.itea.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ua.itea.model.Shape;

public class ShapeDB {
	private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `shapes` "
											   + "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
											   + "`name` VARCHAR(9) NOT NULL, " 
											   + "`perimeter` DOUBLE UNSIGNED NOT NULL, " 
											   + "`area` DOUBLE UNSIGNED NOT NULL, "
											   + "`color` VARCHAR(6) NOT NULL);";
	
	private final static String INSERT = "INSERT INTO `shapes` "
					+ "(`name`, `perimeter`, `area`, `color`) VALUES ('%s', %f, %f, '%s');";
	private final static String COUNT = "SELECT COUNT(*) FROM `shapes`;";
	private final static String SELECT = "SELECT `name`, `perimeter`, `area`, `color` FROM `shapes`;";
	
	private final static String TABLE_NAME = "shapes";
	private final static String[] HEADER = { "name", "perimeter", "area", "color" };
	
	public String[] getTableHeader() {
		return HEADER.clone();
	}
	
	public void addShape(Connection conn, Shape shape) throws SQLException {
		Statement statement = null;
		
		try {
			statement = conn.createStatement();
			statement.execute(CREATE_TABLE);
			statement.execute(String.format(INSERT, shape.getName(),
													shape.getPerimeter(),
													shape.getArea(),
													shape.getColor()));
			statement.close();
			statement = null;
			
			conn.close();
			conn = null;
			
		} catch (SQLException ex) {
	        if (statement != null) {
	        	statement.close();
	        }
	        
	        conn.close();
			throw ex;
		}
	}
	
	public String[][] getShapes(Connection conn) throws SQLException, EmptyTableException {
		String[][] result = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			int numberOfShapes = 0;
			
			statement = conn.createStatement();
			statement.execute(CREATE_TABLE);
			
			resultSet = statement.executeQuery(COUNT);
			if (resultSet.next()) {
				numberOfShapes = resultSet.getInt("COUNT(*)");
			}
			resultSet.close();
			resultSet = null;
			
			if(numberOfShapes == 0) {
				throw new EmptyTableException(TABLE_NAME);
			}
			
			result = new String[numberOfShapes][4];
			resultSet = statement.executeQuery(SELECT);
			for (int i = 0; i < result.length && resultSet.next(); i++) {
				result[i][0] = resultSet.getString(HEADER[0]);
				result[i][1] = resultSet.getString(HEADER[1]);
				result[i][2] = resultSet.getString(HEADER[2]);
				result[i][3] = resultSet.getString(HEADER[3]);	
			}
			resultSet.close();
			resultSet = null;
			
			statement.close();
			statement = null;
			
		} catch (SQLException ex) {
			if (resultSet != null) {
				resultSet.close();
			}
			
	        if (statement != null) {
	        	statement.close();
	        }
	        
	        conn.close();
			throw ex;
		}
		
		return result;
	}
}
