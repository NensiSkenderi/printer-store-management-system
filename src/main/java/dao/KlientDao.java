package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Klient;

public class KlientDao extends DAO {
	public KlientDao() {
		super();
	}

	public void addKlient(Klient klient) throws SQLException {

		String insert_user = "INSERT INTO toner.klient " + 
				"(klienti, nipt, kontakt) VALUES (?,?,?)";
		stm = connector.prepareStatement(insert_user);

		stm.setString(1, klient.getKlienti());
		stm.setString(2, klient.getNipt());
		stm.setString(3, klient.getKontakt());

		stm.executeUpdate();
		stm.close();
	}


	public List<Klient> getKlient() throws SQLException{
		List<Klient> data = new ArrayList<Klient>();
		String query = "SELECT klienti, nipt, kontakt, id FROM toner.klient";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);

		while(rs.next()) {
			Klient r = new Klient();
			r.setKlienti(rs.getString(1));
			r.setNipt(rs.getString(2));
			r.setKontakt(rs.getString(3));
			r.setId(rs.getInt(4));
			
			data.add(r);
		}
		return data;
	}

	public void deleteKlient(int id) throws SQLException{
		String query = "DELETE FROM toner.klient where id=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, id);

		stm.execute();
		stm.close();

	}

	public void updateKlient(Klient k) throws SQLException {
		String update = "UPDATE toner.klient SET klienti = ?, nipt = ?,"
				+ "kontakt = ? WHERE id = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, k.getKlienti());
		stm.setString(2, k.getNipt());
		stm.setString(3, k.getKontakt());
		stm.setInt(4, k.getId());
		

		stm.executeUpdate();
		stm.close();
	}
	
	public List<String> getKlientet() throws SQLException{
		List<String> data = new ArrayList<>();
		String query = "Select klienti from toner.klient ORDER BY klienti;";
		
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);
		
		while(rs.next()) {
			String kategori = rs.getString(1);
			data.add(kategori);
		}
		
		return data;
	}
	
	public int getIdFromKlienti(String klienti) throws Exception {
		String sql_query = "select id,klienti from toner.klient where klienti = '"+klienti+"'";
		stm = connector.prepareStatement(sql_query);
		rs = stm.executeQuery(sql_query);
		
		while(rs.next()) 
			return rs.getInt(1);
		return 0;
	}
}
