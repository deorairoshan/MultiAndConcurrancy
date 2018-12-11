package com.concurrancy.test;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorExample {

	public static void main(String[] args) {
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
		Task task = new Task("Repeat Task");
		System.out.println("Created : " + task.getName());
		executor.scheduleWithFixedDelay(task, 2, 2, TimeUnit.SECONDS); 
		// Thread execution time + delayed time
		// executor.scheduleAtFixedRate(task, 2, 2, TimeUnit.SECONDS); // Thread
		// execution time
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
			Long duration = (long) (Math.random() * 10);
			System.out.println("Executing : " + name + ", Current Seconds : "
					+ Calendar.getInstance().get(Calendar.SECOND) + " Duration : " + duration);
			try {
				TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
