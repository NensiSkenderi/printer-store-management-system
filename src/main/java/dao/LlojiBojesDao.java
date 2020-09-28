package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LlojiBojesDao extends DAO {

	public LlojiBojesDao() {
		super();
	}
	
	public List<String> getLlojiBojes() throws SQLException{
		List<String> data = new ArrayList<>();
		String query = "Select lloji_bojes from toner.lloji_bojra ORDER BY lloji_bojes;";
		
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);
		
		while(rs.next()) {
			String kategori = rs.getString(1);
			data.add(kategori);
		}
		
		return data;
	}
}
