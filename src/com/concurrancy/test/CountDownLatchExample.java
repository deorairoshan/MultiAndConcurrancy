package com.concurrancy.test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
	public static void main(String args[]) throws InterruptedException {

		// Let us create task that is going to
		// wait for four threads before it starts
		CountDownLatch latch = new CountDownLatch(4);

		// Let us create four worker
		// threads and start them.
		Worker first = new Worker(2000, latch, "WORKER-1");
		Worker second = new Worker(4000, latch, "WORKER-2");
		Worker third = new Worker(6000, latch, "WORKER-3");
		Worker fourth = new Worker(8000, latch, "WORKER-4");

		first.start();
		second.start();
		third.start();
		fourth.start();

		// The main task waits for four threads
		latch.await();

		// Wait for 4 seconds
		// latch.await(4, TimeUnit.SECONDS);

		// Main thread has started
		System.out.println(Thread.currentThread().getName() + " has finished");
	}

	private static class Worker extends Thread {
		private int delay;
		private CountDownLatch latch;

		public Worker(int delay, CountDownLatch latch, String name) {
			super(name);
			this.delay = delay;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(delay);
				latch.countDown();
				System.out.println(Thread.currentThread().getName() + " finished");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
