package model;

import java.sql.Date;

public class Arketuar {

	private int id;
	private String menyra;
	private Date created_date;
	
	public Arketuar() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenyra() {
		return menyra;
	}

	public void setMenyra(String menyra) {
		this.menyra = menyra;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	
}
