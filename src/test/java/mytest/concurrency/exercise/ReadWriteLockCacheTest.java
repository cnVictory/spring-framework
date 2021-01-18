package mytest.concurrency.exercise;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于读写锁实现一个懒加载的缓存
 */
public class ReadWriteLockCacheTest {

	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	public String get() {

		String value = null;

		// 读之前加读锁
		rwl.readLock().lock();
		try{
			if (value == null) {

				// 读不到，释放读锁，加写锁
				rwl.readLock().unlock();
				rwl.writeLock().lock();

				try{
					// 为什么要双重检查，因为在上面加写锁的时候，多个线程都在这里等待，第一个线程完成了后，value已经有值了
					// 后面进来的线程就不需要再去写了
					if (value == null) {
						// 真实情况 去DB获取
						value = "数据";
					}
				} finally {
					rwl.writeLock().unlock();
				}

				// 已经写了数据，这里加读锁继续后面读
				rwl.readLock().lock();
			}
		} finally {
			rwl.readLock().unlock();
		}

		return value;
	}
}
