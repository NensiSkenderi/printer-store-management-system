package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.test;

public class BojraDao extends DAO {

	public BojraDao() {
		super();
	}

	public List<test> getBojra() throws SQLException {
		List<test> data = new ArrayList<test>();
		String query = "select ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 

		while(rs.next()) {
			test t = new test();
			t.setId(rs.getInt(1));
			t.setName(rs.getString(2));
			data.add(t);
		}
		return data;
	}

}
