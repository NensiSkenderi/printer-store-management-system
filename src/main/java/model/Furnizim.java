package model;

import java.sql.Date;

public class Furnizim {

	private int id;
	private Bojra bojra_id;
	private double sasia, cmimi, vlera;
	private Date created_date;
	
	public Furnizim() {}
	
	public Furnizim(int id, Bojra bojra_id, double sasia, double cmimi, Date created_date) {
		this.id = id;
		this.bojra_id = bojra_id;
		this.sasia = sasia;
		this.cmimi = cmimi;
		this.created_date = created_date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Bojra getBojra_id() {
		return bojra_id;
	}
	public void setBojra_id(Bojra bojra_id) {
		this.bojra_id = bojra_id;
	}
	public double getSasia() {
		return sasia;
	}
	public void setSasia(double sasia) {
		this.sasia = sasia;
	}
	public double getCmimi() {
		return cmimi;
	}
	public void setCmimi(double cmimi) {
		this.cmimi = cmimi;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public double getVlera() {
		return vlera;
	}

	public void setVlera(double vlera) {
		this.vlera = vlera;
	}
}
