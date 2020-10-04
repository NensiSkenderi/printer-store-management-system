package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import model.Perdoruesit;
import utils.Utils;


public class PerdoruesitDao extends DAO { 

	public PerdoruesitDao() {
		super();
	}
	
	public void addPerdorues(Perdoruesit user) throws SQLException {
		if(this.check_username(user.getUsername())) {
			Utils.alerti("Warning!", "Username exists!", AlertType.WARNING);
			return;
		}
		
		String insert_user = "INSERT INTO toner.perdoruesit " + 
				"(username, password, name, surname, email, rights, telefon) VALUES (?,?,?,?,?,?,?)";
		stm = connector.prepareStatement(insert_user);

		stm.setString(1, user.getUsername());
		stm.setString(2, Utils.encrypt(Utils.key, Utils.initVector, user.getPassword()));
		stm.setString(3, user.getName());
		stm.setString(4, user.getSurname());
		stm.setString(5, user.getEmail());
		stm.setString(6, user.getAccess());
		stm.setString(7, user.getTelefon());
		

		stm.executeUpdate();
		stm.close();
		}
	
	
	public boolean check_username(String username) throws SQLException {
		String query = "select * from toner.perdoruesit where username = '"+username+"'";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery();
		if(rs.next() == true)
			return true;
		else 
			return false;
	}
	
	public List<Perdoruesit> viewPerdoruesit() throws SQLException{
		List<Perdoruesit> data = new ArrayList<Perdoruesit>();
		String query = "SELECT userid, username, name, surname, telefon, email, "
				+ "rights FROM toner.perdoruesit";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);
		
		while(rs.next()) {
			Perdoruesit r = new Perdoruesit();
			r.setUserid(rs.getInt(1));
			r.setUsername(rs.getString(2));
			r.setName(rs.getString(3));
			r.setSurname(rs.getString(4));
			r.setTelefon(rs.getString(5));
			r.setEmail(rs.getString(6));
			r.setAccess(rs.getString(7));
			data.add(r);
		}
		return data;
	}
	
	public void deletePerdoruesit(int userid) throws SQLException{
		String query = "DELETE FROM toner.perdoruesit where userid=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, userid);
		
		stm.execute();
		stm.close();
		
	}

	public void updatePerdorues(Perdoruesit p) throws SQLException {
		String update = "UPDATE toner.perdoruesit SET username = ?, name = ?,"
				+ "surname = ?, telefon = ?, email = ?, rights = ?, password = ? WHERE userid = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, p.getUsername());
		stm.setString(2, p.getName());
		stm.setString(3, p.getSurname());
		stm.setString(4, p.getTelefon());
		stm.setString(5, p.getEmail());
		stm.setString(6, p.getAccess());
		stm.setString(7, Utils.encrypt(Utils.key, Utils.initVector, p.getPassword()));
		
		stm.setInt(8, p.getUserid());
		
		stm.executeUpdate();
		stm.close();
	}
}
