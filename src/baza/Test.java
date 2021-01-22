package baza;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class Test implements Serializable{


	String idTesta;
	TipTesta tipTesta;
	String korisnik;
	GregorianCalendar vreme;
	Status rezultat;
	
	public Test(String id,TipTesta tipTesta, String korisnik, GregorianCalendar vreme,Status rezultat) {
		this.idTesta=id;
		this.tipTesta = tipTesta;
		this.korisnik = korisnik;
		this.vreme = vreme;
		this.rezultat=rezultat;
	}
	

	public String getIdTesta() {
		return idTesta;
	}


	public TipTesta getTipTesta() {
		return tipTesta;
	}

	public String getKorisnik() {
		return korisnik;
	}

	public GregorianCalendar getVreme() {
		return vreme;
	}


	public Status getRezultat() {
		return rezultat;
	}
	

}
