package dao;


public class ControlDAO {

	private static ControlDAO dao = new ControlDAO();
	private KlientDao klient_dao = new KlientDao();
	private ShitjeDao shitje_dao = new ShitjeDao();
	private BojraDao bojra_dao = new BojraDao();
	private FurnizimDao furnizim_dao = new FurnizimDao();
	
	public static ControlDAO getControlDao() {
		return dao;
	}
	
	public KlientDao getKlientDao() {
		return klient_dao;
	}
	
	public ShitjeDao getShitjeDao() {
		return shitje_dao;
	}

	public BojraDao getBojraDao() {
		return bojra_dao;
	}

	public FurnizimDao getFurnizimDao() {
		return furnizim_dao;
	}
	
}
