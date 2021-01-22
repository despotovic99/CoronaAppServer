package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import baza.BazaKorisnika;
import baza.BazaTestova;
import baza.Korisnik;
import baza.PCRTest;
import baza.Status;
import baza.Test;

public class Server {

	private final static String PUTANJAkorisnik = "korisnik.corona";
	private final static String PUTANJAtest = "test.corona";
	private final static String PUTANJApcrtest = "pcrtestovi.corona";

	static int PORT = 9000;
	static ServerSocket serverSocket = null;
	static Socket socket = null;

	//////////////////////////////////////////////////////////////

	static BazaKorisnika bazaKorisnika = new BazaKorisnika();
	static BazaTestova bazaTestova = new BazaTestova();
	static LinkedList<PCRTest> pcrtestoviLinkedList = new LinkedList<>();
	

	public static void main(String[] args) {

		Thread thread = new Thread() {
			public void run() {
				BufferedReader tastatura = new BufferedReader(new InputStreamReader(System.in));

				while (true) {
					try {
						if (tastatura.readLine().equals("*exit")) {
							Server.ugasiSe();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		};

		thread.start();

		try {

			Server.ucitajKorisnike();
			Server.ucitajTestove();
			Server.ucitajPCRTestove();
			
			serverSocket = new ServerSocket(PORT);

			System.out.println("Server je podignut i slusa na portu: " + PORT);

			while (true) {


				socket = serverSocket.accept();
				System.out.println("Konekcija uspostavljena..");

				ClientHandler client = new ClientHandler(socket);

				client.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Server.sacuvaj();
		}

	}

	protected static void ugasiSe() {

		System.out.println("Gasim se");
		System.out.println("Cuvanje fajlova");
		sacuvaj();
		System.out.println("Fajlovi sacuvani, cao");
		System.exit(0);
	}

	private static void sacuvaj() {

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PUTANJAkorisnik));

			for (Korisnik korisnik : bazaKorisnika.getLista()) {
				out.writeObject(korisnik);
			}
			out.flush();
			out.close();

			out = new ObjectOutputStream(new FileOutputStream(PUTANJAtest));

			for (Test test : bazaTestova.getLista()) {
				out.writeObject(test);
			}

			out.flush();
			out.close();

			out = new ObjectOutputStream(new FileOutputStream(PUTANJApcrtest));
			for (PCRTest test : Server.pcrtestoviLinkedList) {
				out.writeObject(test);
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void ucitajKorisnike() {

		bazaKorisnika.obrisiSve();

		

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(PUTANJAkorisnik));

			while (true) {
				Korisnik korisnik = (Korisnik) in.readObject();
				bazaKorisnika.dodajKorisnika(korisnik);
			}
		} catch (Exception e) {
			System.out.println("Gotovo ucitavanje korisnika");
		}

	}

	private static void ucitajTestove() {

		bazaTestova.getLista().clear();

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(PUTANJAtest));

			while (true) {
				Test test = (Test) in.readObject();
				bazaTestova.dodajTest(test);
			}
		} catch (Exception e) {
			System.out.println("Gotovo ucitavanje testova");
		}

	}

	private static void ucitajPCRTestove() {

		pcrtestoviLinkedList.clear();

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(PUTANJApcrtest));

			while (true) {
				PCRTest test = (PCRTest) in.readObject();

				pcrtestoviLinkedList.add(test);
			}
		} catch (Exception e) {
			System.out.println("Gotovo ucitavanje testova");
		}

	}

}
