class Consumer implements Runnable {
    BoundedBuffer bb;

    public Consumer(BoundedBuffer bb) {
        this.bb = bb;
    }

    public void run() {
        String data;
        while (true) {
            data = bb.remove();
        }
    }
}