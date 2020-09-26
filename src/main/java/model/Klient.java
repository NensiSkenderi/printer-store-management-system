package model;

import java.sql.Date;

public class Klient {

	private int id;
	private String klienti, nipt, kontakt;
	private Date created_date;

	public Klient() {

	}

	public Klient(int id, String klienti, String nipt, String kontakt, Date created_date) {
		super();
		this.id = id;
		this.klienti = klienti;
		this.nipt = nipt;
		this.kontakt = kontakt;
		this.created_date = created_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKlienti() {
		return klienti;
	}

	public void setKlienti(String klienti) {
		this.klienti = klienti;
	}

	public String getNipt() {
		return nipt;
	}

	public void setNipt(String nipt) {
		this.nipt = nipt;
	}

	public String getKontakt() {
		return kontakt;
	}

	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

}
