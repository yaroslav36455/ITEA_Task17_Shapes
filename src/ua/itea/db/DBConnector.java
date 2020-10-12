package ua.itea.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:/home/vessel/Documents/"
					  					   + "eclipse-workspace/Task17_Shapes/shapes.db");
	}
}
