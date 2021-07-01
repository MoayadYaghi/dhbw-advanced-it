package Erzeuger_Verbraucher_Problem;

import java.util.concurrent.Semaphore;

/**
 * Organisiert die Fahrt der Z�ge an der Schienenkreisen
 */
public class StreckenManagement {

	Semaphore empty;
	Semaphore full;
	// Gibt an, ob der gemeinsame Abschnitt belegt ist
	boolean mittelstueckBelegt;

	// Konstruktor von Klasse StreckenManagement
	public StreckenManagement(int size) {
		empty = new Semaphore(size, true);
		full = new Semaphore(0, true);
		// Am Anfang ist der Abschnitt frei
		mittelstueckBelegt = false;
	}

	public void enterLok(String lokName) {
		if (lokName == "lok 0") {
			try {
				// Lok 0 pr�fet, ob der gemeinsame Abschnitt frei ist
				// Falls ja, f�hrt Lok 0 direkt in den gemeinsamen Abschnitt
				if (!mittelstueckBelegt) {
					System.out.println("Lok 0 will in den gemeinsamen Abschnitt fahren.");
					empty.acquire();
					// Der Abschnitt wird von Lok 0 belegt
					mittelstueckBelegt = true;
					System.out.println("Lok 0 f�hrt in den gemeinsamen Abschnitt.");
					// Falls nein, muss Lok 0 warten
				} else {
					System.out.println("Lok 0 will in den gemeinsamen Abschnitt fahren.");
					System.out.println("Lok 0 wartet auf Lok 1.");
					empty.acquire();
					System.out.println("Lok 0 f�hrt in den gemeinsamen Abschnitt.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (lokName == "lok 1") {
			try {
				// Lok 1 pr�fet, ob der gemeinsame Abschnitt frei ist
				// Falls ja, f�hrt Lok 1 direkt in den gemeinsamen Abschnitt
				if (!mittelstueckBelegt) {
					System.out.println("Lok 1 will in den gemeinsamen Abschnitt fahren.");
					full.acquire();
					// Der Abschnitt wird von Lok 1 belegt
					mittelstueckBelegt = true;
					System.out.println("Lok 1 f�hrt in den gemeinsamen Abschnitt.");
					// Falls nein, muss Lok 1 warten
				} else {
					System.out.println("Lok 1 will in den gemeinsamen Abschnitt fahren.");
					System.out.println("Lok 1 wartet auf Lok 0.");
					full.acquire();
					System.out.println("Lok 1 f�hrt in den gemeinsamen Abschnitt.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void exitLok(String lokName) {
		// Lok 0 f�hrt aus dem gemeinsamen Abschnitt aus
		if (lokName == "lok 0") {
			System.out.println("Lok 0 f�hrt aus dem gemeinsamen Abschnitt aus.");
			full.release();
			// Der Abschnitt wird von Lok 0 befreit
			mittelstueckBelegt = false;
			System.out.println("Lok 0 gibt den gemeinsamen Abschnitt wieder f�r Lok 1 frei.");
			// Lok 1 f�hrt aus dem gemeinsamen Abschnitt aus
		} else if (lokName == "lok 1") {
			System.out.println("Lok 1 f�hrt aus dem gemeinsamen Abschnitt aus.");
			empty.release();
			// Der Abschnitt wird von Lok 1 befreit
			mittelstueckBelegt = false;
			System.out.println("Lok 1 gibt den gemeinsamen Abschnitt wieder f�r Lok 0 frei.");
		}
	}
}
