package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierTest {

	/**
	 * CyclicBarrier
	 * 用于多个线程阻塞，只有阻塞的线程到达规定的数量，才能继续向下执行
	 * cyclicbarrier.await 是进行等待的代码段
	 */
	@Test
	public void testCyclicBarrier() {

		CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

		int threadNumber = 3;

		ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
		CompletionService completionService = new ExecutorCompletionService(executorService);

		for (int i = 1; i <= threadNumber; i++) {

			completionService.submit(() -> {

				System.out.println(Thread.currentThread().getName() + " 准备出发");
				Thread.sleep(new Random().nextInt(5000));
				System.out.println(Thread.currentThread().getName() + " 到达第一集合地点，等待其他人到来");

				cyclicBarrier.await();

				System.out.println(Thread.currentThread().getName() + " 向第二集合点出发");
				Thread.sleep(new Random().nextInt(5000));
				System.out.println(Thread.currentThread().getName() + " 到达第二集合点，等待其他人到来");

				cyclicBarrier.await();

				System.out.println(Thread.currentThread().getName() + " 向第三集合点出发");
				Thread.sleep(new Random().nextInt(5000));
				System.out.println(Thread.currentThread().getName() + " 到达第三集合点，等待其他人到来");

				cyclicBarrier.await();

				System.out.println(Thread.currentThread().getName() + " 完成");
				return Thread.currentThread().getName();
			});

		}

		for (int i = 0; i < threadNumber; i++) {
			try {
				Future take = completionService.take();
				System.out.println(take.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
