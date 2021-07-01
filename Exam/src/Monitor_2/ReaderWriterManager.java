package Monitor_2;

class ReaderWriterManager {
    public void read() throws InterruptedException {
        ReaderWriterMonitor.startRead();
//lesen
        ReaderWriterMonitor.endRead();
    }

    public void write() throws InterruptedException {
        ReaderWriterMonitor.startWrite();
//schreiben
        ReaderWriterMonitor.endWrite();
    }
}//classRW1