package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Bojra;
import model.LlojiBojes;

public class BojraDao extends DAO {

	public BojraDao() {
		super();
	}

	public List<Bojra> getBojra() throws SQLException {
		List<Bojra> data = new ArrayList<Bojra>();
		String query = "select b.emri, b.id, ll.lloji_bojes, ll.id from toner.bojra b " + 
				"join toner.lloji_bojra ll on b.lloji_bojes_id = ll.id " + 
				"group by ll.lloji_bojes;";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 

		while(rs.next()) {
			LlojiBojes llojiBojes = new LlojiBojes();
			llojiBojes.setId(rs.getInt(4));
			llojiBojes.setLloji_bojes(rs.getString(3));
			
			Bojra bojra = new Bojra();
			bojra.setId(rs.getInt(2));
			bojra.setEmri(rs.getString(1));
			bojra.setLloji_bojes_id(llojiBojes);
			
			data.add(bojra);
		}
		return data;
	}
	
	public void deleteBojra(int id) throws SQLException{
		String query = "DELETE FROM toner.bojra where id=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, id);

		stm.execute();
		stm.close();

	}
	
	public void addBojra(Bojra bojra) throws SQLException {

		String insert_user = "INSERT INTO toner.bojra " + 
				"(emri, lloji_bojes_id) VALUES (?,?)";
		stm = connector.prepareStatement(insert_user);

		stm.setString(1, bojra.getEmri());
		stm.setInt(2, bojra.getLloji_bojes_id().getId());

		stm.executeUpdate();
		stm.close();
	}

	public void updateBojra(Bojra bojra) throws SQLException {
		String update = "UPDATE toner.bojra SET emri = ?, lloji_bojes_id = ?,"
				+ "kontakt = ? WHERE id = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, bojra.getEmri());
		stm.setInt(2, bojra.getLloji_bojes_id().getId());
		stm.setInt(3, bojra.getId());
		

		stm.executeUpdate();
		stm.close();
	}
	
	public int getKategoriId(String kategoriName) throws Exception {
		String sql_query = "select id,kategori_emri from toner.bojra where kategori_emri = '"+kategoriName+"'";
		stm = connector.prepareStatement(sql_query);
		rs = stm.executeQuery(sql_query);
		
		while(rs.next()) 
			return rs.getInt(1);
		return 0;
	}

}
