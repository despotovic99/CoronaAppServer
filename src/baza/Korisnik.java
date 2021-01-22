package baza;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class Korisnik implements Serializable{

	String username, password, ime, prezime, pol, email;
	Status status;
	GregorianCalendar vremePoslednjePrijave;
	//LinkedList<Test> testoviLinkedList = new LinkedList<>();
	
	public Korisnik(String username, String password, String ime, String prezime, String pol, String email,Status status,GregorianCalendar v) {
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.email = email;
		this.status=status;
		this.vremePoslednjePrijave=v;
	}

	
	
	public GregorianCalendar getVremePoslednjePrijave() {
		return vremePoslednjePrijave;
	}



	public void setVremePoslednjePrijave(GregorianCalendar vremePoslednjePrijave) {
		this.vremePoslednjePrijave = vremePoslednjePrijave;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ime+" "+prezime+" "+username+" "+pol.toLowerCase()+" "+ email+" "+status+" "+vremePoslednjePrijave;
	}
	
	
}
