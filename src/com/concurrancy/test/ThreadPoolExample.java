package com.concurrancy.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		for (int i = 0; i < 5; i++) {
			Task task = new Task("Task " + i);
			System.out.println("Created : " + task.getName());
			executor.execute(task);
		}
		executor.shutdown();
	}

	private static class Task implements Runnable {
		private String name;

		public Task(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void run() {
			try {
				Long duration = (long) (Math.random() * 10);
				System.out.println(
						"Executing : " + name + " Duration : " + duration + " Thread : " + Thread.currentThread());
				TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
