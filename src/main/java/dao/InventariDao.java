package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Bojra;
import model.Inventari;
import model.LlojiBojes;

public class InventariDao extends DAO {

	public InventariDao() {
		super();
	}

	public List<Inventari> getInventari() throws SQLException {
		List<Inventari> data = new ArrayList<Inventari>();
		String query = "select i.gjendja, b.id, b.emri from toner.inventari i join toner.bojra b "
				+ "on i.bojra_id = b.id;";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 

		while(rs.next()) {
			Bojra bojra = new Bojra();
			bojra.setId(rs.getInt(2));
			bojra.setEmri(rs.getString(3));
			
			Inventari inventari = new Inventari();
			inventari.setBojra_id(bojra);
			inventari.setGjendja(rs.getDouble(1));
			
			data.add(inventari);
		}
		return data;
	}
	
	public void addGjendje(Inventari inventari) throws SQLException {

		String insert_user = "INSERT INTO toner.inventari " + 
				"(bojra_id, gjendja) VALUES (?,?)";
		stm = connector.prepareStatement(insert_user);

		stm.setInt(1, inventari.getBojra_id().getId());
		stm.setDouble(2, inventari.getGjendja());

		stm.executeUpdate();
		stm.close();
	}


}
