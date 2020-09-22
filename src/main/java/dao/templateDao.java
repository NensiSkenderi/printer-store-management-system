package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.test;

public class templateDao extends DAO {

	public templateDao() {
		super();
	}

	public List<test> getTest() throws SQLException {
		List<test> data = new ArrayList<test>();
		String query = "select id,name from test;";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 

		while(rs.next()) {
			test t = new test();
			t.setId(rs.getInt(1));
			t.setName(rs.getString(2));
			data.add(t);
		}
		
		System.out.println(data.size() + "aaaaaaaa");
		return data;
	}

}
