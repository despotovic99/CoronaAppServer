package paket;

import java.io.Serializable;

public class Paket implements Serializable{

	String zaglavlje;
	String kodPotvrde;
	Object data;
	
	public Paket(String zaglavlje, String kodPotvrde, Object data) {
		this.zaglavlje = zaglavlje;
		this.kodPotvrde = kodPotvrde;
		this.data = data;
	}

	public String getZaglavlje() {
		return zaglavlje;
	}

	public String getKodPotvrde() {
		return kodPotvrde;
	}

	public Object getData() {
		return data;
	}
	
	
	
	
	
}
