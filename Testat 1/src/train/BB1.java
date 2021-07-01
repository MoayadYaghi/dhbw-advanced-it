package train;

import java.util.concurrent.Semaphore;

public class BB1 {
	private int size;
	private String[] buffer;
	private int ctr = 0;
	private int nextfree = 0;
	private int nextfull = 0;
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore full = new Semaphore(0, true);
	private Semaphore empty;

	public BB1(int size) {
		this.size = size;
		buffer = new String[size];
		empty = new Semaphore(size, true);
	}

	public void append(String data) {
		try {
			System.out.println("Producer arriving");
			empty.acquire();
			mutex.acquire();
			System.out.println("Producer active with " + data);
			buffer[nextfree] = data;
			nextfree = (nextfree + 1) % size;
			ctr++;
			System.out.println("counter: " + ctr);
			mutex.release();
			full.release();
			System.out.println("Producer gone");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}// append

	public String remove() {
		String data = "";
		try {
			System.out.println("Consumer arriving");
			full.acquire();
			mutex.acquire();
			System.out.println("Consumer active");
			data = buffer[nextfull];
			nextfull = (nextfull + 1) % size;
			ctr--;
			System.out.println("counter: " + ctr);
			mutex.release();
			empty.release();
			System.out.println("Consumer gone with " + data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}// remove

	public static void main(String[] args) {
		BB1 bb = new BB1(5);
		new Thread(new Consumer(bb)).start();
		new Thread(new Producer(bb)).start();
	}// main
}// class
