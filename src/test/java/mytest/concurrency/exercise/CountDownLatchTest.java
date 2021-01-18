package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CountDownLatchTest {

	@Test
	public void testCountDownLatch() {

		int number = 3;

		CountDownLatch startCount = new CountDownLatch(number);
		CountDownLatch endCount = new CountDownLatch(number);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(number);

		ExecutorService executorService = Executors.newFixedThreadPool(number);
		CompletionService completionService = new ExecutorCompletionService(executorService);
		for (int i = 0; i < number; i++) {
			completionService.submit(new Callable() {
				@Override
				public Object call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					System.out.println(Thread.currentThread().getName() + " 做好准备");
					startCount.countDown();
					cyclicBarrier.await();

					Thread.sleep(new Random().nextInt(5000));
					System.out.println(Thread.currentThread().getName() + " 准备冲刺");
					endCount.countDown();

					return null;
				}
			});
		}

		System.out.println(Thread.currentThread().getName() + " 裁判准备发令 ");
		try {
			startCount.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " 比赛开始，等待运动员冲过终点线");
		try {
			endCount.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " 比赛结束");
	}
}
