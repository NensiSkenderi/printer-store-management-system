package dao;

import java.sql.SQLException;

import model.Perdoruesit;
import utils.Utils;


public class LoginDao extends DAO {

	public LoginDao() {
		super();
	}
	
	
	public String get_pass() throws SQLException {
		String pass = "";
		String sql = "SELECT password FROM toner.perdoruesit  where userid = '"+Utils.idP+"' ";
		stm = connector.prepareStatement(sql);
		rs = stm.executeQuery(sql);
		
		while(rs.next()) {
			pass =  Utils.decrypt(Utils.key, Utils.initVector, rs.getString(1));
		}
		return pass;
	}
	
	public boolean check_user_and_pass(String user,String pass) {
		String sql = "SELECT username, password, userid , rights, name from toner.perdoruesit  WHERE username = ? and password = ? ";

		try {

			stm = connector.prepareStatement(sql);
			stm.setString(1, user);
			stm.setString(2, pass);
			rs = stm.executeQuery();

			while(rs.next()) {
				Utils.username = rs.getString(1);
				Utils.idP = rs.getInt(3);
				Utils.rights = rs.getString(4);
				Utils.name = rs.getString(5);
				return rs.getString(1).equals(user) && rs.getString(2).equals(pass);
			}
			stm.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}
	
	public void update_password(Perdoruesit p) throws SQLException {
		String update_password = "update toner.perdoruesit set password = ? where userid = ?";

		stm = connector.prepareStatement(update_password);
		stm.setString(1, Utils.encrypt(Utils.key, Utils.initVector,p.getPassword()));
		stm.setInt(2, Utils.idP);

		stm.executeUpdate();
		stm.close();
	}
	
	public void flush() throws SQLException{
		String query = "flush hosts;";
		stm = connector.prepareStatement(query);
		
		stm.execute();
		stm.close();

	}
}
