import java.io.File;
import java.util.concurrent.Semaphore;

public class BMVmitZuweisungderDrucker {

    int anz = -1;
    private Semaphore printer;
    private Semaphore mutex = new Semaphore(1, true);
    private boolean[] printerFree;

    public BMVmitZuweisungderDrucker(int anz) {
        this.anz = anz;
        printer = new Semaphore(anz, true);
        for (int i = 0; i < anz; i++)
            printerFree[i] = true;
    }

    public void printFile(File f) {
        int usePrinterNo = -1;
        try {
            printer.acquire();
            mutex.acquire();
            for (int i = 0; i < anz; i++)
                if (printerFree[i]) {
                    usePrinterNo = i;
                    printerFree[usePrinterNo] = false;
                    break;
                }
            mutex.release();
            // f.printOnPrinter(usePrinterNo);
            mutex.acquire();
            printerFree[usePrinterNo] = true;
            mutex.release();
            printer.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//printFile
}