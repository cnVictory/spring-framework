package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.*;

public class SimpleTraditionalThread {

	@Test
	public void testSimpleTraditionalThread1() {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				super.run();
				System.out.println("new Thread");
				System.out.println("Current Thread" + Thread.currentThread().getState());
				System.out.println("Current Thread" + this.getState() + ":" + this.getName());
			}
		};
		t1.start();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Current Thread" + Thread.currentThread().getState());
			}
		});
		t2.start();

		System.out.println("~~~~~~~~~~~~~~~~~~~  分隔符 ~~~~~~~~~~~~~~~~");


		// 先运行这个子类的方法，如果找不到子类重写的方法，就去找父类的方法
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("runnable run ");
			}
		}) {
			@Override
			public void run() {
				System.out.println("t3 ~  run ");
			}
		};
		t3.start();

		System.out.println("~~~~~~~~~~~~~~~~~~~  分隔符 ~~~~~~~~~~~~~~~~");

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("bombing");
			}
		}, 10);

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("current time " + new Date());
		}

	}

	@Test
	public void testTraditionalThreadSynchronized() {

		Outputer outputer = new Outputer();
		int timer = 50;

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < timer; i++) {
					outputer.output("zhangxiaoxiang");
				}
			}
		}).start();

		while (true) {

		}

	}

	static class Outputer {
		public void output(String s) {
			synchronized (this) {
				System.out.print(Thread.currentThread().getName() + "  ");
				for (int i = 0; i < s.length(); i++) {
					System.out.print(s.charAt(i));
				}
				System.out.println();
			}
		}
	}

	@Test
	public void testTraditionalSynchronized2() {
		final Outputer outputer = new Outputer();
		int timer = 50;

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					outputer.output("zhangxiaoxiang");
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					outputer.output("lihuoming");
				}
			}
		}).start();

		while (true) {

		}
	}

	/**
	 * 主线程循环5次，子线程循环3次， 再主线程5次，再子线程3次，如此循环往复50次
	 */
	@Test
	public void testTradtionalSynchronized3() {

		Object lock = new Object();
		final List<Boolean> flagList = new ArrayList<>(Arrays.asList(true));

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					synchronized (lock) {
						if (flagList.get(0)) {
							try {
								lock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						for (int j = 1; j <= 3; j++) {
							System.out.println(Thread.currentThread().getName() + " loop of " + i + " - " + j);
						}
						flagList.set(0, true);
						lock.notify();
					}
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			synchronized (lock) {
				if (!flagList.get(0)) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 5; j++) {
					System.out.println(Thread.currentThread().getName() + " loop of " + i + " - " + j);
				}
				flagList.set(0, false);
				lock.notify();
			}
		}
	}

	private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

	@Test
	public void testThreadLocal() {
		threadLocal.set(1);
		Integer i = threadLocal.get();
	}
}
