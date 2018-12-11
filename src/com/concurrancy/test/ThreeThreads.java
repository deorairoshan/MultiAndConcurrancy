package com.concurrancy.test;

public class ThreeThreads {

	public static void main(String[] args) {

		Thread t1 = new Thread(new Thread1(1));
		Thread t2 = new Thread(new Thread1(2));
		Thread t3 = new Thread(new Thread1(3));

		try {
			t1.start();
			Thread.sleep(500);
			t2.start();
			Thread.sleep(500);
			t3.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

class Thread1 implements Runnable {

	int startIndex;

	public Thread1(int startIndex) {
		this.startIndex = startIndex;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(startIndex);
			startIndex += 3;
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
