import java.io.File;
import java.util.concurrent.Semaphore;

public class Bmv {
    private Semaphore printer;

    public Bmv(int anz) {
        printer = new Semaphore(anz, true);
    }

    public void printFile(File f) {
        try {
            printer.acquire();
            // f.print();//AUF WELCHEM DRUCKER???
            printer.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}//class