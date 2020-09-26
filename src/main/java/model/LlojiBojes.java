package model;

public class LlojiBojes {

	private int id;
	private String lloji_bojes;
	
	public LlojiBojes() {}
	
	public LlojiBojes(int id, String lloji_bojes) {
		this.id = id;
		this.lloji_bojes = lloji_bojes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLloji_bojes() {
		return lloji_bojes;
	}

	public void setLloji_bojes(String lloji_bojes) {
		this.lloji_bojes = lloji_bojes;
	}
	
	
}
