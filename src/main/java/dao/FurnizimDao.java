package dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.Bojra;
import model.Furnizim;

public class FurnizimDao extends DAO {
	public FurnizimDao() {
		super();
	}

	public DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	public List<Furnizim> getFurnizim() throws SQLException {
		List<Furnizim> data = new ArrayList<Furnizim>();
		String query = "select f.id, f.sasia, f.cmimi, b.id, b.emri, f.created_date " + 
				"from toner.furnizim f join toner.bojra b " + 
				"group by f.id;";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 

		while(rs.next()) {
			Bojra bojra = new Bojra();
			bojra.setId(rs.getInt(4));
			bojra.setEmri(rs.getString(5));
			
			Furnizim furnizim = new Furnizim();
			furnizim.setId(rs.getInt(1));
			furnizim.setSasia(rs.getDouble(2));
			furnizim.setCmimi(rs.getDouble(3));
			furnizim.setCreated_date(rs.getDate(6));
			furnizim.setBojra_id(bojra);
			furnizim.setVlera(Double.parseDouble(decimalFormat.format(furnizim.getSasia() * furnizim.getCmimi())));
			
			data.add(furnizim);
		}
		return data;
	}
}
