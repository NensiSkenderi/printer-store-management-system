package model;

public class Inventari {

	private Bojra bojra_id;
	private double gjendja;
	
	public Inventari() {}
	
	public Inventari(Bojra bojra_id, double gjendja) {
		this.bojra_id = bojra_id;
		this.gjendja = gjendja;
	}

	public Bojra getBojra_id() {
		return bojra_id;
	}

	public void setBojra_id(Bojra bojra_id) {
		this.bojra_id = bojra_id;
	}

	public double getGjendja() {
		return gjendja;
	}

	public void setGjendja(double gjendja) {
		this.gjendja = gjendja;
	}
	
	
}
