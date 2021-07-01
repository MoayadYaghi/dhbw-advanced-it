package private_semaphore;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * Organisiert die Fahrt der Z�ge an der Schienenkreisen
 */
public class StreckenManagement {
	
	Semaphore[] privSem;
	Semaphore mutex;
	// Gibt an, ob der gemeinsame Abschnitt belegt ist
	boolean mittelstueckBelegt;
	boolean[] wartet;
	// Gibt an welche Lok zuletzt den gemeinsamen Abschnitt verlassen hat.
	boolean[] lokWarZuletzt = new boolean[2];

	// Konstruktor von Klasse StreckenManagement
	public StreckenManagement(int size) {
		// Lok 0 f�hrt zu erst in den gemeinsamen Abschnitt, d.h. Lok 1 hat den g.A.
		// zuLetzt verlassen.
		lokWarZuletzt[0] = false;
		lokWarZuletzt[1] = true;
		// Am Anfang ist der Abschnitt frei
		mittelstueckBelegt = false;
		mutex = new Semaphore(1, true);
		privSem = new Semaphore[size];
		wartet = new boolean[size];
		Arrays.fill(wartet, false);
		for (int i = 0; i < size; i++) {
			privSem[i] = new Semaphore(0, true);
		}
	}

	public void enterLok(String lokName) {
		if (lokName == "lok 0") {
			try {
				// Eintirttsprotokoll von Lok 0
				System.out.println("Lok 0 f�hrt in den eingangs-kritischen Abschnitt.");
				mutex.acquire();
				// Pr�fen, ob der gemeinsame Abschnitt nicht belegt ist und Lok 1 zuletzt den
				// Abschnitt verlassen hat
				System.out.println("Lok 0 pr�ft, ob sie in den gemeinsamen Abschnitt druchfahren kann.");
				if (!mittelstueckBelegt && lokWarZuletzt[1]) {
					// Nachher nicht warten
					privSem[0].release();
					// Der Abschnitt wird von Lok 0 belegt
					mittelstueckBelegt = true;
					System.out.println("Lok 0 darf durchfahren.");
				} else {
					// Lok 0 tr�gt einen Belegungsw�nsch ein
					wartet[0] = true;
					System.out.println("Lok 0 muss auf Lok 1 warten.");
				}
				System.out.println("Lok 0 verl�sst den eingangs-kritischen Abschnitt.");
				mutex.release();
				// Lok 0 wartet ggf.
				privSem[0].acquire();
				System.out.println("Lok 0 f�hrt in den gemeinsamen Abschnitt.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (lokName == "lok 1") {
			try {
				// Eintrittsprotokoll von Lok 1
				System.out.println("Lok 1 f�hrt in den eingangs-kritischen Abschnitt.");
				mutex.acquire();
				// Pr�fen, ob der gemeinsame Abschnitt nicht belegt ist und Lok 0 zuletzt den
				// Abschnitt verlassen hat
				System.out.println("Lok 1 pr�ft, ob sie druchfahren kann.");
				if (!mittelstueckBelegt && lokWarZuletzt[0] == true) {
					// Nachher nicht warten
					privSem[1].release();
					// Der Abschnitt wird von Lok 1 belegt
					mittelstueckBelegt = true;
					System.out.println("Lok 1 darf durchfahren.");
				} else {
					// Lok 1 tr�gt einen Belegungsw�nsch ein
					wartet[1] = true;
					System.out.println("Lok 1 muss auf Lok 0 warten.");
				}
				System.out.println("Lok 1 verl�sst den eingangs-kritischen Abschnitt.");
				mutex.release();
				// Lok 1 wartet ggf.
				privSem[1].acquire();
				System.out.println("Lok 1 f�hhrt in den gemeinsamen Abschnitt.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//
	public void exitLok(String lokName) {
		if (lokName == "lok 0") {
			try {
				// Austrittsprotokoll von Lok 0
				mutex.acquire();
				System.out.println("Lok 0 f�hrt aus dem gemeinsamen Abschnitt aus.");
				System.out.println("Lok 0 f�hrt in den ausgangs-kritischen Abschnitt.");
				// Lok 0 gibt den gemeinsamen Abschnitt frei
				mittelstueckBelegt = false;
				// Lok 0 hat den gemeinsamen Abschnitt zuletzt verlassen
				lokWarZuletzt[0] = true;
				lokWarZuletzt[1] = false;
				System.out.println("Lok 0 pr�ft, ob Lok 1 wartet.");
				// Falls Lok 1 wartet, wird sie aufgeweckt
				if (wartet[1]) {
					System.out.println("Lok 1 wartet und wird aufgeweckt.");
					mittelstueckBelegt = true;
					wartet[1] = false;
					privSem[1].release();
				}
				System.out.println("Lok 0 verl�sst den ausgangs-kritischen Abschnitt.");
				mutex.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (lokName == "lok 1") {
			try {
				// Austrittsprotokoll von Lok 1
				mutex.acquire();
				// Lok 1 gibt den gemeinsamen Abschnitt frei
				System.out.println("Lok 1 f�hrt aus dem gemeinsamen Abschnitt aus.");
				System.out.println("Lok 1 f�hrt in den ausgangs-kritischen Abschnitt.");
				mittelstueckBelegt = false;
				// Lok 1 hat den gemeinsamen Abschnitt zuletzt verlassen
				lokWarZuletzt[1] = true;
				lokWarZuletzt[0] = false;
				// Falls Lok 0 wartet, wird sie aufgeweckt
				System.out.println("Lok 1 pr�ft, ob Lok 0 wartet.");
				if (wartet[0]) {
					System.out.println("Lok 0 wartet und wird aufgeweckt.");
					mittelstueckBelegt = true;
					wartet[0] = false;
					privSem[0].release();
				}
				System.out.println("Lok 1 verl�sst den ausgangs-kritischen Abschnitt.");
				mutex.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}