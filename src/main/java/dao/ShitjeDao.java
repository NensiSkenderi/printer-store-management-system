package dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.Arketuar;
import model.Bojra;
import model.Klient;
import model.Shitje;

public class ShitjeDao extends DAO {

	public ShitjeDao() {
		super();
	}
	
	public DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	public List<Shitje> getShitje() throws SQLException {
		List<Shitje> data = new ArrayList<Shitje>();
		String query = "select sh.lloji_fatures, sh.created_date, b.emri, b.id, " + 
				"sh.sasia, sh.cmimi, k.klienti, k.id, sh.id, a.id, a.menyra, sh.date_likujduar " + 
				"from toner.shitje sh join toner.bojra b " + 
				"on sh.bojra_id = b.id join toner.arketuar a " + 
				"on sh.arketim_id = a.id join toner.klient k " + 
				"on sh.klient_id = k.id where sh.deleted = 0";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 

		while(rs.next()) {
			Bojra bojra = new Bojra();
			bojra.setId(rs.getInt(4));
			bojra.setEmri(rs.getString(3));
			
			Klient klient = new Klient();
			klient.setId(rs.getInt(8));
			klient.setKlienti(rs.getString(7));
			
			Arketuar arketuar = new Arketuar();
			arketuar.setId(rs.getInt(10));
			arketuar.setMenyra(rs.getString(11));
			
			Shitje shitje = new Shitje();
			shitje.setLloji_fatures(rs.getString(1));
			shitje.setCreated_date(rs.getDate(2));
			shitje.setBojra_id(bojra);
			shitje.setSasia(rs.getDouble(5));
			shitje.setCmimi(rs.getDouble(6));
			shitje.setVlera(Double.parseDouble(decimalFormat.format(shitje.getSasia() * shitje.getCmimi())));
			shitje.setKlient_id(klient);
			shitje.setId(rs.getInt(9));
			shitje.setArketim_id(arketuar);
			shitje.setDate_likujduar(rs.getDate(12));
			
			data.add(shitje);
		}
		
		return data;
	}
	
	public void addShitje(Shitje shitje) throws SQLException {
		String insertBojra = "INSERT INTO toner.shitje " + 
				"(lloji_fatures, klient_id, arketim_id, date_likujduar, bojra_id, sasia, cmimi)"
				+ " VALUES (?,?,?,?,?,?,?)";
		stm = connector.prepareStatement(insertBojra);
		stm.setString(1, shitje.getLloji_fatures());
		stm.setInt(2, shitje.getKlient_id().getId());
		stm.setInt(3, shitje.getArketim_id().getId());
		stm.setDate(4, shitje.getDate_likujduar());
		stm.setInt(5, shitje.getBojra_id().getId());
		stm.setDouble(6, shitje.getSasia());
		stm.setDouble(7, shitje.getCmimi());

		stm.execute();
		stm.close();
	}

	public void updateShitje(Shitje shitje) throws SQLException {
		String update = "UPDATE toner.shitje SET lloji_fatures = ?, klient_id = ?, arketim_id = ?, date_likujduar = ?, "
				+ "bojra_id = ?, sasia = ?, cmimi = ?  WHERE id = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, shitje.getLloji_fatures());
		stm.setInt(2, shitje.getKlient_id().getId());
		stm.setInt(3, shitje.getArketim_id().getId());
		stm.setDate(4, shitje.getDate_likujduar());
		stm.setInt(5, shitje.getBojra_id().getId());
		stm.setDouble(6, shitje.getSasia());
		stm.setDouble(7, shitje.getCmimi());
		
		stm.setInt(8, shitje.getId());

		stm.executeUpdate();
		stm.close();
	}
	
	public void deleteShitje(int id) throws SQLException {
		String query = "DELETE FROM toner.shitje where id=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, id);

		stm.execute();
		stm.close();
		
	}

}
