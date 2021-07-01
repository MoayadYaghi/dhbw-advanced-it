package PrivateSemaphore_forExam;

public class Main {
	
	Lok lok0;
	Lok lok1;
	StreckenManagement streckenManagement;

	public Main() {
		streckenManagement = new StreckenManagement(2);
		// Anlegen von Lok-Objekten und Attribute eingeben
		lok0 = new Lok(0, streckenManagement, 2500);
		lok1 = new Lok(1, streckenManagement, 3000);

		// Threads starten
		new Thread(lok0).start();
		new Thread(lok1).start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
