package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

	private static DatabaseConnection instance = new DatabaseConnection();
	private Connection conn;
	
	private DatabaseConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toner?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static DatabaseConnection instance() {
        if (instance == null) {
        	instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }
	
	
}

