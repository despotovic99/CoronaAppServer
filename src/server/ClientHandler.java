package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PrimitiveIterator.OfDouble;
import java.util.zip.InflaterInputStream;

import baza.Korisnik;
import baza.PCRTest;
import baza.Status;
import baza.Test;
import paket.Paket;


public class ClientHandler extends Thread {

	static Socket socket = null;
	
	 ObjectInputStream clientObjIn=null;
	 ObjectOutputStream clientObjOut=null;
	 
	 BufferedReader clientIn=null;
	 PrintStream clientOut=null;
	 
	 Korisnik korisnikCH=null;
	 
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		
	
		try {
		
		clientObjOut= new ObjectOutputStream(socket.getOutputStream());
		
		
		clientObjIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
	
		
		
		while(true) {
			
			
			Paket paket =(Paket) clientObjIn.readObject();
			String izbor = paket.getZaglavlje();
			
			switch (izbor) {
			
			case "prijava":
				Korisnik k = (Korisnik)paket.getData();
				String username = k.getUsername();
				String password=k.getPassword();
				
				Korisnik korisnik = Server.bazaKorisnika.daLiPostojiKorisnik(username, password);
				
				if(korisnik == null) {
					clientObjOut.writeObject(new Paket("prijava", "nePostoji", null));
				}else{
					clientObjOut.writeObject(new Paket("prijava", "postoji", korisnik));
					System.out.println(korisnik.toString());
					korisnikCH=korisnik;
					
				}
					
				clientObjOut.flush();
				
				break;
				
			case "registracija" :
				
				Korisnik k2 = (Korisnik)paket.getData();	
				
				if(Server.bazaKorisnika.daLiPostojiKorisnikEmail(k2.getEmail())==null 
						&& Server.bazaKorisnika.daLiPostojiKorisnikUsername(k2.getUsername())==null) {
					Server.bazaKorisnika.dodajKorisnika(k2);
					clientObjOut.writeObject(new Paket(izbor, "uspesno", k2));
					
				}else {
					String poruka ="\nGreska";
					
					if(Server.bazaKorisnika.daLiPostojiKorisnikEmail(k2.getEmail())!=null) {
						poruka+=" korisnik sa email-om "+k2.getEmail()+" vec postoji\n";
					}
					if(Server.bazaKorisnika.daLiPostojiKorisnikUsername(k2.getUsername())!=null) {
						poruka+="korisnik sa username "+k2.getUsername()+" vec postoji\n";
					}
					System.out.println(poruka);
					clientObjOut.writeObject(new Paket(izbor, "neuspesno", poruka));
					
				}
				
				
				clientObjOut.flush();
				
				break;
				
			case "sacuvajKorisnika" :
				
				Korisnik korisnik2 = (Korisnik)paket.getData();
				Server.bazaKorisnika.updateKorisnika(korisnik2);
				//System.out.println(korisnik2.toString());
				break;
				
			case "zapocniPCR":
				
				PCRTest test=(PCRTest)paket.getData();
						clientObjOut.writeObject(new Paket("pcrStanje", "none", "Poslato"));
						
						Server.pcrtestoviLinkedList.add(test);
						
						
						clientObjOut.writeObject(new Paket("pcrStanje", "none", "U obradi"));
						
				break;
				
			case "zahtevZaPCR":
				
				String userName = (String) paket.getData();
				PCRTest test1 = null;
				String kodPotvrde="nePostoji";
				
				for (PCRTest tPcrTest : Server.pcrtestoviLinkedList) {
					if(tPcrTest.getUser().equals(userName)) {
						test1=tPcrTest;
						kodPotvrde="postoji";
					}
				}
				
				clientObjOut.writeObject(new Paket("zahtevZaPCR", kodPotvrde, test1));
				
				break;
				
			case "obrisiPCR":
				String userObrisi=(String)paket.getData();
				
				Iterator<PCRTest>  it= Server.pcrtestoviLinkedList.iterator();
				
				while(it.hasNext()) {
					PCRTest p = it.next();
					if(p.getUser().equals(userObrisi)) {
						it.remove();
					}
				}
				
				break;
				
			case "statusIzmena":
				Status status = (Status)paket.getData();
				Server.bazaKorisnika.vratiKorisnika(korisnikCH.getUsername()).setStatus(status);
				break;
				
			case "testoviKorisnika":
				
				String user = (String)paket.getData();
				LinkedList<Test> listaZaSlanje =Server.bazaTestova.vratiTestoveKorisnika(user);
				clientObjOut.writeObject(new Paket("testoviKorisnika", "uspesno", listaZaSlanje));
				
				break;
				
			case "klijentSaljeTest":
				
				Test test2 =(Test)paket.getData();
				Server.bazaTestova.dodajTest(test2);
				LinkedList<Test> listaZaSlanje2 =Server.bazaTestova.vratiTestoveKorisnika(test2.getKorisnik());
				clientObjOut.writeObject(new Paket("testoviKorisnika", "uspesno", listaZaSlanje2));

				break;
				
			case "statistika":
				
				String[] statistika = new String[6];
				String[] statTest =  Server.bazaTestova.vratiStatistiku();
				String[] statKorisnik =  Server.bazaKorisnika.vratiStatistiku();
				statistika[0]=statTest[0];
				statistika[1]=statTest[1];
				statistika[2]=statTest[2];
				statistika[3]=statKorisnik[0];
				statistika[4]=statKorisnik[1];
				statistika[5]=statKorisnik[2];
				clientObjOut.writeObject(new Paket("statistika", "uspesno", statistika));
				
				break;
				
			case "sviKorisnici":
				LinkedList<Korisnik> listazaSlanjeKorisnik= Server.bazaKorisnika.vratiSveKorisnike();
				clientObjOut.writeObject(new Paket("sviKorisnici", "uspesno", listazaSlanjeKorisnik));
				for (Korisnik kk :listazaSlanjeKorisnik) {
					System.out.println(">>>"+kk.toString());
				}
				break;
				
			default:
				break;
			}
			continue;
			
			
		}
		
		
			
		} catch (IOException e) {
			
			System.out.println("Klijent se diskonektovao!");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	
	

}
