package train;

public class Producer implements Runnable {
	BB1 bb;

	public Producer(BB1 bb) {
		this.bb = bb;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			bb.append("Data " + i);
		} // for
	}// run
}
