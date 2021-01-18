package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

	private String data;

	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	@Test
	public void testReadWriteLock() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				get();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				get();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				get();
			}
		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				set();
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				set();
//			}
//		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				set();
			}
		}).start();
		while (true) {

		}
	}

	public void get() {
		while (true) {
			try {
				rwl.readLock().lock();
				System.out.println(Thread.currentThread().getName() + " prepare read ~~");
//				Thread.sleep(new Random().nextInt(5000));
				System.out.println(Thread.currentThread().getName() + " read " + this.data);
				System.out.println(Thread.currentThread().getName() + " finish read ~~");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
			} finally {
				rwl.readLock().unlock();
			}
		}
	}

	public void set() {
		while (true) {
			try {
				rwl.writeLock().lock();
				System.out.println(Thread.currentThread().getName() + " prepare write ~~");
//				Thread.sleep(new Random().nextInt(5000));
				this.data = "test";
				System.out.println(Thread.currentThread().getName() + " write " + this.data);
				System.out.println(Thread.currentThread().getName() + " finish write ~~");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
			} finally {
				rwl.writeLock().unlock();
			}
		}
	}
}
