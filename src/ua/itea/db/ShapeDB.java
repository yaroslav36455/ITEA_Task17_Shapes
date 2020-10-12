package ua.itea.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		ArrayList<String[]> table = new ArrayList<String[]>();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = conn.createStatement();
			statement.execute(CREATE_TABLE);
			
			resultSet = statement.executeQuery(SELECT);
			
			while (resultSet.next()) {
				table.add(new String[] {
						resultSet.getString(HEADER[0]),
						resultSet.getString(HEADER[1]),
						resultSet.getString(HEADER[2]),
						resultSet.getString(HEADER[3])
				});
			}
			
			resultSet.close();
			resultSet = null;
			
			statement.close();
			statement = null;
			
			if(table.size() == 0) {
				throw new EmptyTableException(TABLE_NAME);
			}
			
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
		
		String[][] arrTable = new String[table.size()][];
		
		for (int i = 0; i < arrTable.length; i++) {
			arrTable[i] = table.get(i);
		}
		
		return arrTable;
	}
}
