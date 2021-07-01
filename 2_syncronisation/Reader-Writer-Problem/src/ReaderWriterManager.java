import java.io.File;
import java.util.concurrent.Semaphore;

public class ReaderWriterManager {
    private File f;
    private int readcount = 0;//countingallreaders
    private Semaphore mutex = new Semaphore(1, true);
    private Semaphore w = new Semaphore(1, true);//mutexforwriters

    public ReaderWriterManager(File f) {
        this.f = f;
    }

    public void write(int index, byte[] data) throws InterruptedException {
        w.acquire();
        // f.write(index, data);//k.A.
        w.release();
    }//write

    public int read(int index, byte[] data) throws InterruptedException {
        mutex.acquire();
        readcount++;
        if (readcount == 1) w.acquire();//first reader
        mutex.release();
        // int byteCount = f.read(index, data);//k.A.
        int byteCount = 0; // Just a MOCK!!!
        mutex.acquire();
        readcount--;
        if (readcount == 0) w.release();//last reader
        mutex.release();
        return byteCount;
    }//read
}