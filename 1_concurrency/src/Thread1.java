public class Thread1 extends Thread {

    private int id; // Thread ID

    public Thread1(int id) {
        this.id = id;
    }

    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (Exception e) {
        }
        System.out.println("Hello World (ID = " + id + ")");
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            Thread t = new Thread1(i);
            t.start();
        }
    }
}
