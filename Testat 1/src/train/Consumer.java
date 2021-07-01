package train;

public class Consumer implements Runnable {

	BB1 bb;

	public Consumer(BB1 bb) {
		this.bb = bb;
	}

	public void run() {
		String data;
		while (true) {
			data = bb.remove();
		}
	}// run
}// class
