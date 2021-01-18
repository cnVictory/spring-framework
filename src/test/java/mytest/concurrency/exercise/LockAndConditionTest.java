package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockAndConditionTest {

	static class Outputer {

		private Lock lock = new ReentrantLock();

		public void output(String s) {

			lock.lock();

			try {
				System.out.print(Thread.currentThread().getName() + "  ");
				for (int i = 0; i < s.length(); i++) {
					System.out.print(s.charAt(i));
				}
				System.out.println();
			} finally {
				lock.unlock();
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

	@Test
	public void testTraditionalSynchronized() {
		Business business = new Business();
		new Thread(() -> {
			for (int i = 1; i <= 50; i++) {
				business.sub(i);
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.main(i);
		}
	}

	class Business {

		private boolean flag = true;

		private Object lock = new Object();

		void main(int i) {
			synchronized (lock) {
				if (flag) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 5; j++) {
					System.out.println(Thread.currentThread().getName() + " main " + j + " of " + i);
				}
				flag = !flag;
				lock.notify();
			}
		}

		void sub(int i) {
			synchronized (lock) {
				if (!flag) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 3; j++) {
					System.out.println(Thread.currentThread().getName() + " main " + j + " of " + i);
				}
				flag = !flag;
				lock.notify();
			}
		}
	}

	class SubBusiness {

		private boolean flag = true;

		private Lock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		void main(int i) {
			lock.lock();
			try{
				while (!flag) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 5; j++) {
					System.out.println(Thread.currentThread().getName() + j + " of " + i);
				}
				flag = !flag;
				condition.signal();
			} finally {
				lock.unlock();
			}
		}

		void sub(int i) {
			lock.lock();
			try{
				while (flag) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 3; j++) {
					System.out.println(Thread.currentThread().getName() + j + " of " + i);
				}
				flag = !flag;
				condition.signal();
			} finally {
				lock.unlock();
			}
		}
	}


	@Test
	public void testCondition() {
		SubBusiness subBusiness = new SubBusiness();
		new Thread(() -> {
			for (int i = 1; i <= 50; i++) {
				subBusiness.sub(i);
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			subBusiness.main(i);
		}
	}
}
