package com.concurrancy.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {

	private static class Producer implements Runnable {

		private BlockingQueue<String> queue;

		public Producer(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					queue.put("Msg" + i);
					TimeUnit.SECONDS.sleep(10);
				}
				queue.put("Exit");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private static class Consumer implements Runnable {

		private BlockingQueue<String> queue;

		public Consumer(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				String msg = queue.take();
				while (!msg.equals("Exit")) {
					System.out.println(msg);
					msg = queue.take();
					TimeUnit.SECONDS.sleep(1);
				}
				System.out.println("Exit");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);

		new Thread(producer).start();
		new Thread(consumer).start();

	}
}
