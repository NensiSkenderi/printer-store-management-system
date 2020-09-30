package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArketimDao extends DAO {

	public ArketimDao() {
		super();
	}
	
	public List<String> getArketimet() throws SQLException{
		List<String> data = new ArrayList<>();
		String query = "Select menyra from toner.arketuar ORDER BY menyra;";
		
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);
		
		while(rs.next()) {
			String kategori = rs.getString(1);
			data.add(kategori);
		}
		
		return data;
	}
	
	public int getIdFromArketim(String menyra) throws Exception {
		String sql_query = "select id,menyra from toner.arketuar where menyra = '"+menyra+"'";
		stm = connector.prepareStatement(sql_query);
		rs = stm.executeQuery(sql_query);
		
		while(rs.next()) 
			return rs.getInt(1);
		return 0;
	}

}
