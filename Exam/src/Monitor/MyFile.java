package Monitor;

public class MyFile {
    String wdir = "/Users/pagnia/FileServer"; // base dir
    String fileName;
    private int anzR = 0;
    private int anzW = 0;
    private boolean activeW = false;

    public MyFile(String fileName) {
        this.fileName = fileName;
    }

    public static synchronized MyFile getHandle(MyFile[] fArray, String fname) throws Exception {
        MyFile myfile = null;
        for (int i = 0; (i <= 100); i++) { // check for 'filename'
            if (i == 100)
                throw new Exception("Monitor.MyFile array overflow; max 100 files");
            if (fArray[i] == null) // insert filename
                fArray[i] = new MyFile(fname);
            if (fArray[i].fileName.equals(fname)) {
                myfile = fArray[i];
                break;
            }
        } // for
        return myfile;
    } // getHandle

    public synchronized void startRead() {
        while (anzW > 0)
            try {
                this.wait();
            } catch (Exception e) {
                System.err.println(e);
                // e.printStackTrace();
            }
        anzR++;
    }

    public synchronized void endRead() {
        anzR--;
        if (anzR == 0)
            this.notifyAll();
    }

    public synchronized void startWrite() {
        anzW++;
        while ((anzR > 0) || (activeW))
            try {
                this.wait();
            } catch (Exception e) {
                System.err.println(e);
            }
        activeW = true;
    }

    public synchronized void endWrite() {
        anzW--;
        activeW = false;
        this.notifyAll();
    }
}

