package dao;

public class ControlDAO {

	private static ControlDAO dao = new ControlDAO();
	private KlientDao klient_dao = new KlientDao();
	private ShitjeDao shitje_dao = new ShitjeDao();
	private BojraDao bojra_dao = new BojraDao();
	private FurnizimDao furnizim_dao = new FurnizimDao();
	private LlojiBojesDao lloji_bojes_dao = new LlojiBojesDao();
	private InventariDao inventari_dao = new InventariDao();
	private ArketimDao arketim_dao = new ArketimDao();
	
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
	
	public LlojiBojesDao getLlojiBojes() {
		return lloji_bojes_dao;
	}

	public InventariDao getInventariDao() {
		return inventari_dao;
	}
	
	public ArketimDao getArketimDao() {
		return arketim_dao;
	}
}
