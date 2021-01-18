package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

	@Test
	public void testLockSupport() {
		Thread t = new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				try {
					Thread.sleep(i * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i == 6) {
					LockSupport.park();
				}
				System.out.println(i);
			}
		});

		t.start();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LockSupport.unpark(t);

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("结束");
	}

}
