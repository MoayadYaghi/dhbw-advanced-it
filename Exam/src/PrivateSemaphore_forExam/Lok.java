package PrivateSemaphore_forExam;

public class Lok implements Runnable {
	
	int lokName;
	int fahrZeit;
	private final StreckenManagement StreckenManagement;

	// Konstruktor von Klasse Lok
	public Lok(int name, StreckenManagement StreckenManagement, int lokFahrZeit) {
		this.lokName = name;
		this.StreckenManagement = StreckenManagement;
		this.fahrZeit = lokFahrZeit;
	}

	private void fahren() {
		System.out.println(lokName + " startet.");
		while (true) {
			try {
				Thread.sleep(fahrZeit); // fahren auﬂerhalb der gemeinsamen Abschnitt 
				StreckenManagement.enterLok(lokName);
				Thread.sleep(fahrZeit); // fahren innerhalb der gemeinsamen Abschnitt 
				StreckenManagement.exitLok(lokName);
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
