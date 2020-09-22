package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnection {

	private static dbConnection instance = new dbConnection();
	private Connection conn;
	
	private dbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static dbConnection instance() {
        if (instance == null) {
        	instance = new dbConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }
	
	
}

