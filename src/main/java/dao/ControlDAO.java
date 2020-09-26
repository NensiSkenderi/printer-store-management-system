package dao;


public class ControlDAO {

	private static ControlDAO dao = new ControlDAO();
	private KlientDao KlientDao = new KlientDao();
	private static templateDao template_dao = new templateDao();
	private static ShitjeDao shitje_dao = new ShitjeDao();
	
	public static ControlDAO getControlDao() {
		return dao;
	}
	
	public KlientDao getKlientDao() {
		return KlientDao;
	}
	
	public ShitjeDao getShitjeDao() {
		return shitje_dao;
	}
	
}
