package Erzeuger_Verbraucher_Problem;

public class Lok implements Runnable {
	
	String lokName;
	int fahrZeit;
	private final StreckenManagement streckenManagement;

	// Konstruktor von Klasse Lok
	public Lok(String name, StreckenManagement streckenManagement, int lokFahrZeit) {
		this.lokName = name;
		this.streckenManagement = streckenManagement;
		this.fahrZeit = lokFahrZeit;
	}

	private void fahren() {
		System.out.println(lokName + " startet.");
		while (true) {
			try {
				Thread.sleep(fahrZeit); // fahren au�erhalb der gemeinsamen Abschnitt 
				streckenManagement.enterLok(lokName);
				Thread.sleep(fahrZeit); // fahren innerhalb der gemeinsamen Abschnitt 
				streckenManagement.exitLok(lokName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		fahren();
	}

}
