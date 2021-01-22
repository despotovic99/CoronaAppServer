package baza;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class PCRTest implements Serializable{

	private String user;
	private GregorianCalendar vremePocetka;
	private GregorianCalendar vremeZavrsetka;

	public GregorianCalendar getVremePocetka() {
		return vremePocetka;
	}

	public void setVremePocetka(GregorianCalendar vremePocetka) {
		this.vremePocetka = vremePocetka;
	}

	public GregorianCalendar getVremeZavrsetka() {
		return vremeZavrsetka;
	}

	public void setVremeZavrsetka(GregorianCalendar vremeZavrsetka) {
		this.vremeZavrsetka = vremeZavrsetka;
	}

	public PCRTest(GregorianCalendar vremePocetka, GregorianCalendar vremeZavrsetka, String user) {

		this.user = user;
		this.vremePocetka = vremePocetka;
		this.vremeZavrsetka = vremeZavrsetka;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
