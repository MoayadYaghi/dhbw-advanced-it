public class Thread2 implements Runnable {

    private int id; // Thread ID

    public Thread2(int id) {
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
            Thread2 runnable = new Thread2(i);
            // Thread(Runnable target) - Thread takes a runnable
            // Thread2 implements Runnable => Instance from Thread2 is a Runnable instance
            // That why it works to instantiate a Thread2 object an give it to Thread
            Thread t = new Thread(runnable);
            // Runnable runnable = new Thread2(i); would work exactly the same
            t.start();
        }
    }
}

