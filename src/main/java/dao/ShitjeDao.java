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
				"sh.sasia, sh.cmimi, k.klienti, k.id, sh.id, a.id, a.menyra " + 
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
			
			data.add(shitje);
		}
		
		return data;
	}

}
