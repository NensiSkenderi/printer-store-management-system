package dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import model.Bojra;
import model.Furnizim;
import utils.Utils;

public class FurnizimDao extends DAO {
	public FurnizimDao() {
		super();
	}

	public DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	public List<Furnizim> getFurnizim() throws SQLException {
		List<Furnizim> data = new ArrayList<Furnizim>();
		String query = "select f.id, f.sasia, f.cmimi, b.id, b.emri, f.created_date " + 
				"from toner.furnizim f join toner.bojra b on f.bojra_id = b.id " + 
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
	
	public void addFurnizim(Furnizim furnizim) throws SQLException {
		if(this.checkBoja(furnizim.getBojra_id().getEmri())) {
			Utils.alerti("Kujdes!", "Boja nk ekziston!", AlertType.WARNING);
			return;
		}
		String insert_user = "INSERT INTO toner.furnizim " + 
				"(sasia, cmimi, bojra_id) VALUES (?,?,?)";
		stm = connector.prepareStatement(insert_user);

		stm.setDouble(1, furnizim.getSasia());
		stm.setDouble(2, furnizim.getCmimi());
		stm.setInt(3, furnizim.getBojra_id().getId());

		stm.executeUpdate();
		stm.close();
	}

	public void updateFurnizim(Furnizim furnizim) throws SQLException {
		String update = "UPDATE toner.furnizim SET sasia = ?, cmimi = ?, bojra_id = ? WHERE id = ?";
		stm = connector.prepareStatement(update);

		stm.setDouble(1, furnizim.getSasia());
		stm.setDouble(2, furnizim.getCmimi());
		stm.setInt(3, furnizim.getBojra_id().getId());
		
		stm.setInt(4, furnizim.getId());
		

		stm.executeUpdate();
		stm.close();
	}

	public void deleteFurnizim(int id) throws SQLException {
		String query = "DELETE FROM toner.furnizim where id=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, id);

		stm.execute();
		stm.close();
		
	}
	
	public int getIdFromBoja(String emri) throws Exception {
		String sql_query = "select id,emri from toner.bojra where emri = '"+emri+"'";
		stm = connector.prepareStatement(sql_query);
		rs = stm.executeQuery(sql_query);
		
		while(rs.next()) 
			return rs.getInt(1);
		return 0;
	}
	
	public boolean checkBoja(String emri) throws SQLException {
		String query = "select * from toner.bojra where emri = '"+emri+"'";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery();
		if(rs.next() == false)
			return true;
		else 
			return false;
	}
}
