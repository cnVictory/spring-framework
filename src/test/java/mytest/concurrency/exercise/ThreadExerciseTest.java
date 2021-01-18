package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class ThreadExerciseTest {

	/**
	 * 实现一个容器，提供2个方法 add  size
	 * 写2个线程，线程1添加10个元素到容器中，
	 * 线程2实现监控元素的个数，当元素个数为5时，线程2给出提示并且退出
	 */
	@Test
	public void test1() {

		Object o = new Object();

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					synchronized (o) {
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (i == 6) {
							o.notify();
							try {
								o.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println(i);
					}
				}
			}
		});

		t.start();

		synchronized (o) {
			try {
				o.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("结束");
			o.notify();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testThreadInterrupt() {
//		LockSupport.park(this);
		System.out.println(Thread.currentThread().isInterrupted());
		boolean interrupted = Thread.interrupted();
		System.out.println("interrupted = " + interrupted);
		System.out.println(Thread.currentThread().isInterrupted());
		Thread.currentThread().interrupt();
		System.out.println(Thread.currentThread().isInterrupted());
		boolean flag = Thread.interrupted();
		System.out.println("flag = " + flag);
		boolean flag1 = Thread.interrupted();
		System.out.println("flag1 = " + flag1);
		Thread.currentThread().interrupt();
		System.out.println(Thread.currentThread().isInterrupted());
		Thread.currentThread().interrupt();
		System.out.println(Thread.currentThread().isInterrupted());
	}


	@Test
	public void testThreadInterrupt1() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " -- park ");
				LockSupport.park();
				System.out.println(Thread.currentThread().getName() + " -- awake ,isInterrupt = " + Thread.currentThread().isInterrupted());
				System.out.println(Thread.currentThread().getName() + " -- awake ,isInterrupt = " + Thread.interrupted());
				System.out.println(Thread.currentThread().getName() + " -- awake ,isInterrupt = " + Thread.currentThread().isInterrupted());

			}
		});
		t.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " -- unpark other thread");

		// 当使用unPark来唤醒的时候， 线程t的中断状态为false
		// 整个的测试用例打印的结果是：
		/*
			Thread-0 -- park
			main -- unpark other thread
			Thread-0 -- awake ,isInterrupt = false
			Thread-0 -- awake ,isInterrupt = false
			Thread-0 -- awake ,isInterrupt = false
			over
		 */
//		LockSupport.unpark(t);

		// 当线程被中断 interrupt()  时，线程的中断状态为true
		// 用例的打印结果是：
		/*
			Thread-0 -- park
			main -- unpark other thread
			Thread-0 -- awake ,isInterrupt = true
			Thread-0 -- awake ,isInterrupt = true
			Thread-0 -- awake ,isInterrupt = false
			over
		 */
		t.interrupt();

		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("over");
	}
}
