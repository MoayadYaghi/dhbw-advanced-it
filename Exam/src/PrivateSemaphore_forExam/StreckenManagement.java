package PrivateSemaphore_forExam;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * Organisiert die Fahrt der Zuege an der Schienenkreisen
 */
public class StreckenManagement {

    private Semaphore[] privSem;            //////////// 2 SEMAPHORES
    private Semaphore mutex;

    private boolean mittelstueckBelegt;     //////////// 2 BOOLEANS
    private boolean[] wartet;

    private int nextThreadId;

    public StreckenManagement(int size) {
        privSem = new Semaphore[size];
        for (int i = 0; i < size; i++)
            privSem[i] = new Semaphore(0, true);
        mutex = new Semaphore(1, true);

        mittelstueckBelegt = false;
        wartet = new boolean[size];
        Arrays.fill(wartet, false);

        nextThreadId = 0; // weil 0 thread startet
    }

    public void enterLok(int threadID) {
        try {
            mutex.acquire();
            if (!mittelstueckBelegt && threadID == nextThreadId) {
                privSem[threadID].release();
                mittelstueckBelegt = true;
            } else {
                wartet[threadID] = true;
            }
            mutex.release();
            privSem[threadID].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exitLok(int threadID) {
        try {
            mutex.acquire();
            nextThreadId = (threadID + 1) % 2;
            mittelstueckBelegt = false;
            if (wartet[nextThreadId]) {
                mittelstueckBelegt = true;
                wartet[nextThreadId] = false;
                privSem[nextThreadId].release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}