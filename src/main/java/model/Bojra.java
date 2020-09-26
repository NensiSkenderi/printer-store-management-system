package model;

import java.sql.Date;

public class Bojra {

	private int id;
	private String emri;
	private Date created_date;
	private LlojiBojes lloji_bojes_id;
	
	public Bojra() {}

	public Bojra(int id, String emri, Date created_date, LlojiBojes lloji_bojes_id) {
		this.id = id;
		this.emri = emri;
		this.created_date = created_date;
		this.lloji_bojes_id = lloji_bojes_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmri() {
		return emri;
	}

	public void setEmri(String emri) {
		this.emri = emri;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public LlojiBojes getLloji_bojes_id() {
		return lloji_bojes_id;
	}

	public void setLloji_bojes_id(LlojiBojes lloji_bojes_id) {
		this.lloji_bojes_id = lloji_bojes_id;
	}
	
}
