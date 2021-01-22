package baza;

import java.util.LinkedList;

import javax.swing.text.html.HTMLDocument.Iterator;

public class BazaKorisnika {

	LinkedList<Korisnik> lista = new LinkedList<>();

	public BazaKorisnika() {
		
		
	}
	
	
	
	public LinkedList<Korisnik> getLista() {
		return lista;
	}

	public LinkedList<Korisnik> vratiSveKorisnike() {

		LinkedList<Korisnik> linkedList = new LinkedList<>();
		
		for (Korisnik korisnik : lista) {
			linkedList.add(korisnik);
		}
		return linkedList;
	}



	public void dodajKorisnika(Korisnik k) {
		lista.add(k);
	}
	
	public void obrisiKorisnika(Korisnik k) {
		
		java.util.Iterator<Korisnik> it = lista.iterator();
		
		while (it.hasNext()) {
			Korisnik korisnik = it.next();
			if(korisnik.getUsername().equals(k.getUsername())) {
				it.remove();
			}
			
		}
		
		
		
	}
	
	public void updateKorisnika(Korisnik k) {
		obrisiKorisnika(k);
		dodajKorisnika(k);
	}
	
	public Korisnik daLiPostojiKorisnik(String u,String p) {
		
		for (Korisnik korisnik : lista) {
			if(korisnik.getUsername().equals(u) && korisnik.getPassword().equals(p)) {
				return korisnik;
			}
			
		}
		
		return null;
	}
	public Korisnik daLiPostojiKorisnikUsername(String u) {
		
		for (Korisnik korisnik : lista) {
			if(korisnik.getUsername().equals(u)) {
				return korisnik;
			}
			
		}
		
		return null;
	}
	public Korisnik daLiPostojiKorisnikEmail(String u) {
		
		for (Korisnik korisnik : lista) {
			if(korisnik.getEmail().equals(u)) {
				return korisnik;
			}
			
		}
		
		return null;
	}
	
	public Korisnik vratiKorisnika(String username) {
		
		for (Korisnik korisnik : lista) {
			if(korisnik.getUsername().equals(username)) {
				return korisnik;
			}
			
		}
		
		return null;
	}
	
	public String[] vratiStatistiku() {
		
		int nadzor=0;
		int pozKor=0;
		int negKor=0;
		for (Korisnik korisnik : lista) {
			if(korisnik.getStatus().equals(Status.POD_NADZOROM)) nadzor++;
			else if(korisnik.getStatus().equals(Status.POZITIVAN)) pozKor++;
			else if(korisnik.getStatus().equals(Status.NEGATIVAN)) negKor++;
		}
		String[] podaci = {nadzor+"",pozKor+"",negKor+""};
		return podaci;
	}



	public void obrisiSve() {
	lista.clear();
		
	}
	

	
	
	
}
