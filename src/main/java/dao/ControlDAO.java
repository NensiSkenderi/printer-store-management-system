package dao;


public class ControlDAO {

	private static ControlDAO dao = new ControlDAO();
	private static templateDao template_dao = new templateDao();
	
	public static ControlDAO getControlDao() {
		return dao;
	}
	
	public templateDao getTemplateDao() {
		return template_dao;
	}
	
}
