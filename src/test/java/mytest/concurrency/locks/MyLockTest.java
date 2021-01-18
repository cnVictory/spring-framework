package mytest.concurrency.locks;

import org.junit.Test;

public class MyLockTest {

	@Test
	public void test1() {
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		try{
			lock.lock();
			condition.await();

			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	@Test
	public void test2() {
		ReadWriteLock lock = new ReentrantReadWriteLock();
		try{
			lock.readLock().lock();
			lock.writeLock().lock();
		} finally {
			lock.readLock().unlock();
			lock.writeLock().unlock();
		}
	}
}
