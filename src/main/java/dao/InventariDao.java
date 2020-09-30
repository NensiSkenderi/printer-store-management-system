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
		String query = "select sum(i.gjendja), b.id, b.emri, ll.id, ll.lloji_bojes "
				+ "from toner.inventari i join toner.bojra b " + 
				"on i.bojra_id = b.id join toner.lloji_bojra ll "
				+ "on ll.id = b.lloji_bojes_id group by b.emri;";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 

		while(rs.next()) {
			LlojiBojes llojiBojes = new LlojiBojes();
			llojiBojes.setId(rs.getInt(4));
			llojiBojes.setLloji_bojes(rs.getString(5));
			
			Bojra bojra = new Bojra();
			bojra.setId(rs.getInt(2));
			bojra.setEmri(rs.getString(3));
			bojra.setLloji_bojes_id(llojiBojes);
			
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
	
	public void updateGjendje(Inventari inventari) throws SQLException {
		String insert_user = "update toner.inventari set gjendja = ? where bojra_id = ?";
		stm = connector.prepareStatement(insert_user);
		
		stm.setDouble(1, inventari.getGjendja());
		stm.setInt(2, inventari.getBojra_id().getId());
	
		stm.executeUpdate();
		stm.close();
	}

	public double getGjendja(Bojra bojra) throws SQLException {
		String sql_query = "select gjendja from toner.inventari where bojra_id = '"+bojra.getId()+"'";
		stm = connector.prepareStatement(sql_query);
		rs = stm.executeQuery(sql_query);
		
		while(rs.next()) 
			return rs.getDouble(1);
		return 0;
	}

}
