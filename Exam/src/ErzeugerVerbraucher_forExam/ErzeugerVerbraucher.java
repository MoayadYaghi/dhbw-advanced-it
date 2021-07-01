package ErzeugerVerbraucher_forExam;

import java.util.concurrent.Semaphore;

public class ErzeugerVerbraucher { // 100 Correct
    public static Semaphore empty = new Semaphore(1);
    public static Semaphore full = new Semaphore(0);

    public static void enter1() throws InterruptedException {
        empty.acquire();
    }

    public static void exit1() {
        full.release();
    }

    public static void enter2() throws InterruptedException {
        full.acquire();
    }

    public static void exit2() {
        empty.release();
    }
}
