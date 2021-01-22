package baza;

import java.util.LinkedList;

public class BazaTestova {

	LinkedList<Test> lista = new LinkedList<>();
	
	
	
	public LinkedList<Test> getLista() {
		return lista;
	}

	public int brTestova() {
		return lista.size();
	}
	
	public void dodajTest(Test t) {
		lista.add(t);
	}
	
	public void obrisiTest(Test t) {
		
		java.util.Iterator<Test> it = lista.iterator();
		
		while (it.hasNext()) {
			Test test = it.next();
			if(true) {
				it.remove();
			}
			
		}
	}
	
	public Test vratiTest(String id) {
		
		for (Test test : lista) {
			if(test.getIdTesta()==id) {
				return test;
			}
		}
		
		return null;
	}
	
	public LinkedList<Test> vratiTestoveKorisnika(String username){
		
		LinkedList<Test> l = new LinkedList<>();
		
		for (Test test : lista) {
			if(test.getKorisnik().equals(username)) {
				l.add(test);
			}
		}
		
		return l;
	}

	public String[] vratiStatistiku() {
		
		int svi=lista.size();
		int pozitivni=0;
		int negativni=0;
		
		for (Test test : lista) {
			if(test.getRezultat().equals(Status.POZITIVAN)) pozitivni++; 
			else if(test.getRezultat().equals(Status.NEGATIVAN)) negativni++;
			
		}
		
		String[] stat = {svi+"",pozitivni+"",negativni+""};
		return stat;
	}
	
	
	
}
