import java.util.concurrent.locks.Condition;

class ReaderWriterMonitor {
    static boolean activeWriter = false;
    static int readCtr = 0;
    static Condition reader;
    static Condition writer;

    static void startRead() throws InterruptedException {
        readCtr++;
        if (activeWriter)
            reader.wait();
        reader.signal();
    }

    static void endRead() {
        readCtr--;
        if (readCtr == 0)
            writer.signal();
    }

    static void startWrite() throws InterruptedException {
        if (readCtr > 0 || activeWriter)
            writer.wait();
        activeWriter = true;
    }

    static void endWrite() {
        activeWriter = false;
        //LESER PRIORITAET
        if (readCtr > 0)
            reader.signal();
        else
            writer.signal();
    }
}//monitor