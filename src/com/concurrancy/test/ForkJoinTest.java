package com.concurrancy.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ForkJoinTest {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();

		FolderProcessor folderProcessor = new FolderProcessor("C:\\Windows", "a");
		forkJoinPool.execute(folderProcessor);

		do {
			System.out.printf("******************************************\n");
			System.out.printf("Main: Parallelism: %d\n", forkJoinPool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", forkJoinPool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", forkJoinPool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", forkJoinPool.getStealCount());
			System.out.printf("******************************************\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!folderProcessor.isDone());

		forkJoinPool.shutdown();
		List<String> results;
		results = folderProcessor.join();
		System.out.printf("System: %d files found.\n", results.size());
		System.out.println("Total Time: " + (System.currentTimeMillis() - start));
	}

	private static class FolderProcessor extends RecursiveTask<List<String>> {

		private static final long serialVersionUID = 1L;
		private String path;
		private String search;

		public FolderProcessor(String path, String search) {
			this.path = path;
			this.search = search;
		}

		@Override
		protected List<String> compute() {
			// TODO Auto-generated method stub
			List<String> list = new ArrayList<>();
			List<FolderProcessor> tasks = new ArrayList<>();

			File file = new File(path);
			File content[] = file.listFiles();

			if (content != null) {
				for (int i = 0; i < content.length; i++) {
					if (content[i].isDirectory()) {
						FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), search);
						task.fork();
						tasks.add(task);
					} else {
						if (checkFile(content[i].getName())) {
							list.add(content[i].getAbsolutePath());
						}
					}
				}
			}

			addResultsFromTasks(list, tasks);

			return list;
		}

		private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
			for (FolderProcessor task : tasks) {
				list.addAll(task.join());
			}
		}

		private boolean checkFile(String name) {
			return name.startsWith(search);
		}
	}
}
