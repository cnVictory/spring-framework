package mytest.concurrency.exercise;

import org.junit.Test;

public class MultiThreadSharedataTest {

	/** 一共100张票 */
	private static int ticket = 100;

	private int i = 0;

	/**
	 * 3个窗口卖票，100张
	 */
	@Test
	public void testMultiThreadShareData() {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					while (ticket > 0) {
						ticket--;
						System.out.println(Thread.currentThread().getName() + " 卖票，剩余" + ticket);
					}
				}
			}
		};
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		Thread t3 = new Thread(runnable);
		t1.start();
		t2.start();
		t3.start();

		while (true) {

		}
	}

	/**
	 * 三个线程互相唤醒
	 */
	@Test
	public void testMultiThreadCorrespondence() {

		Object lock = new Object();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					synchronized (lock) {
						while (i % 3 != 1) {
							try {
								lock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println(Thread.currentThread().getName());
						i++;
						lock.notifyAll();
					}

				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					synchronized (lock) {
						while (i % 3 != 2) {
							try {
								lock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println(Thread.currentThread().getName());
						i++;
						lock.notifyAll();
					}
				}
			}
		});

		t1.start();
		t2.start();

		while (true) {
			synchronized (lock) {
				while (i % 3 != 0) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName());
				i++;
				lock.notifyAll();
			}
		}

	}
}
