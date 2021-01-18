package mytest.concurrency.exercise;

import org.junit.Test;

public class VolatileTest {

	static class MyInstance {

		private volatile MyInstance instance = null;

		private MyInstance() {

		}

		public MyInstance getInstance() {
			if (instance == null) {
				synchronized (MyInstance.class) {
					if (instance == null) {
						instance = new MyInstance();
					}
				}
			}
			return instance;
		}

	}

	@Test
	public void test1() {
		Object o = new Object();
	}

}
