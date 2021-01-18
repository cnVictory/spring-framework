package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

public class SemaphoreTest {

	/**
	 * Semaphore信号量
	 * 不管外面有多少线程，使用semaphore.acquire   semaphore.release 中间的代码，只能有固定数量的线程同时进入
	 * 其他多余的线程要等里面的线程 semaphore.release 以后才能进入
	 * semaphore信号量用于控制并发的数量
	 */
	@Test
	public void testSemaphore() {

		Semaphore semaphore = new Semaphore(3);
		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			Future<String> submit = executorService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {

					System.out.println(Thread.currentThread().getName() + " 准备进入,当前剩余信号灯：" + (semaphore.availablePermits()));

					// 获取信号灯
					semaphore.acquire();

					System.out.println(Thread.currentThread().getName() + " 进入,当前剩余信号灯：" + (semaphore.availablePermits()));
					Thread.sleep(new Random().nextInt(5000));
					System.out.println(Thread.currentThread().getName() + " 即将退出");

					// 释放信号灯
					semaphore.release();
					System.out.println(Thread.currentThread().getName() + " 退出，剩余信号灯" + semaphore.availablePermits());
					return "test";
				}
			});
		}

		while (true) {

		}
	}
}
