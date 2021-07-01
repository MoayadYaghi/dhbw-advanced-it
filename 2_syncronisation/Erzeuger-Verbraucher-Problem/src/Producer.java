class Producer implements Runnable {
    BoundedBuffer bb;

    public Producer(BoundedBuffer bb) {
        this.bb = bb;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            bb.append("Data" + i);
        }//for
    }//run
}//class