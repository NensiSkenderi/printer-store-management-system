package model;

import java.sql.Date;

public class Shitje {

	private int id;
	private String lloji_fatures;
	private Date created_date;
	private java.util.Date date_likujduar;
	private double sasia, cmimi, vlera;
	private boolean deleted;
	private Arketuar arketim_id;
	private Bojra bojra_id;
	private Klient klient_id;
	private Date emptyDate;
	
	public Shitje() {}
	
	public Shitje(int id, String lloji_fatures, Date created_date, java.util.Date date_likujduar, double sasia, double cmimi,
			boolean deleted, Arketuar arketim_id, Bojra bojra_id, Klient klient_id, Date emptyDate) {
		this.id = id;
		this.lloji_fatures = lloji_fatures;
		this.created_date = created_date;
		this.date_likujduar = date_likujduar;
		this.sasia = sasia;
		this.cmimi = cmimi;
		this.deleted = deleted;
		this.arketim_id = arketim_id;
		this.bojra_id = bojra_id;
		this.klient_id = klient_id;
		this.emptyDate = emptyDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLloji_fatures() {
		return lloji_fatures;
	}

	public void setLloji_fatures(String lloji_fatures) {
		this.lloji_fatures = lloji_fatures;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public java.util.Date getDate_likujduar() {
		return date_likujduar;
	}

	public void setDate_likujduar(java.util.Date date_likujduar) {
		this.date_likujduar = date_likujduar;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Arketuar getArketim_id() {
		return arketim_id;
	}

	public void setArketim_id(Arketuar arketim_id) {
		this.arketim_id = arketim_id;
	}

	public Bojra getBojra_id() {
		return bojra_id;
	}

	public void setBojra_id(Bojra bojra_id) {
		this.bojra_id = bojra_id;
	}

	public double getVlera() {
		return vlera;
	}

	public void setVlera(double vlera) {
		this.vlera = vlera;
	}

	public Klient getKlient_id() {
		return klient_id;
	}

	public void setKlient_id(Klient klient_id) {
		this.klient_id = klient_id;
	}

	public Date getEmptyDate() {
		return emptyDate;
	}

	public void setEmptyDate(Date emptyDate) {
		this.emptyDate = emptyDate;
	}

	
	
}
