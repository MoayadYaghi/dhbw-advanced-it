import java.io.File;
import java.util.concurrent.Semaphore;

public class PrintJobManagement {

    private boolean printerFree = true;
    private File[] files; // Verwaltung der auszudruckenden Dateien
    private boolean[] waiting; // Verwaltung,welche Threads warten
    private Semaphore[] privSem; // privateSemaphore
    private Semaphore mutex = new Semaphore(1, true); // wegen Verwaltungsdaten
    private static final int NO_ID = -1; // undefinierte Thread−ID

    public PrintJobManagement(int threadCount) {
        waiting = new boolean[threadCount];
        privSem = new Semaphore[threadCount];
        files = new File[threadCount];
        for (int i = 0; i < threadCount; i++) {
            waiting[i] = false;
            files[i] = null;
            privSem[i] = new Semaphore(0, true);
        }
    } // Konstruktor

    public void print(File f, int threadId) throws InterruptedException {
        mutex.acquire(); // Eintrittsprotokoll
        if (printerFree) { // Drucker reservieren
            printerFree = false;
            privSem[threadId].release(); // nachher NICHT warten! ctr = 1
        } else { // Belegungswunsch eintragen
            waiting[threadId] = true;
            files[threadId] = f;
        }
        mutex.release();
        privSem[threadId].acquire(); // ggf. warten ctr = 0
        // k.A.jetzt Drucken der Datei f
        mutex.acquire(); // Austrittsprotokoll
        printerFree = true; // Drucker wieder freigeben
        files[threadId] = null;
        // suche wartenden Thread mit dem kuerzesten Druckauftrag
        int fileLength = Integer.MAX_VALUE;
        int selectedThread = NO_ID;
        for (int i = 0; i < waiting.length; i++)
            if (waiting[i] && files[i].length() <= fileLength) {
                selectedThread = i;
                fileLength = (int) files[i].length();
            }
        // falls ein Thread gefunden wurde, wird er nun aufgeweckt
        if (selectedThread != NO_ID) { // If a thread was selected
            waiting[selectedThread] = false;
            printerFree = false;
            privSem[selectedThread].release();
        }
        mutex.release();
    } // print
} // PrintJobManagement
