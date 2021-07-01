import java.io.File;
import java.util.concurrent.Semaphore;

public class ReaderWriterManager {
    private File f;
    private int readcount = 0;
    private int writecount = 0;
    private Semaphore mutex1 = new Semaphore(1, true);
    private Semaphore mutex2 = new Semaphore(1, true);
    private Semaphore mutex3 = new Semaphore(1, true);
    private Semaphore w = new Semaphore(1, true);
    private Semaphore r = new Semaphore(1, true);

    // Semaphore r for writer priority
    public ReaderWriterManager(File f) {
        this.f = f;
    }

    public void read(int index, byte[] data) throws InterruptedException {
        mutex3.acquire();
        r.acquire();
        mutex1.acquire();
        readcount = readcount + 1;
        if (readcount == 1) w.acquire();
        mutex1.release();
        r.release();
        mutex3.release();
//        int byteCount=f.read(index,data);//k.A.
        mutex1.acquire();
        readcount = readcount - 1;
        if (readcount == 0) w.release();
        mutex1.release();
    } // read

    public void write(int index, byte[] data) throws InterruptedException {
        mutex2.acquire();
        writecount = writecount + 1;
        if (writecount == 1) r.acquire();
        mutex2.release();
        w.acquire();
//        f.write(index,data);//k.A.
        w.release();
        mutex2.acquire();
        writecount = writecount - 1;
        if (writecount == 0) r.release();
        mutex2.release();
    } // write
}//class