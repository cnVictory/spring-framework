package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFutureTask {

	int i = 0;

	@Test
	public void testExecutors() {

		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<Integer> futureResult = executorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(5000);
				return ++i;
			}
		});

		System.out.println("正在等待结果");

		try {
			System.out.println("waiting.....");
			Integer result = futureResult.get();
			System.out.println("得到结果：" + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
	}

	@Test
	public void testCompletionService() {

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CompletionService completionService = new ExecutorCompletionService(executorService);
		for (int i = 0; i < 10; i++) {
			completionService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return Thread.currentThread().getName();
				}
			});
		}

		for (int i = 0; i < 10; i++) {
			try {
				Future future = completionService.take();
				Object o = future.get();
				System.out.println("结果" + o);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
