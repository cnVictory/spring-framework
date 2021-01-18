package mytest.concurrency.exercise;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListAndSet {

	/**
	 * 无锁的数据结构
	 * 1.读写分离，拷贝一份进行写操作
	 * 2. 最终一致性
	 * 3. 使用复制拷贝，另外开辟空间，解决并发冲突
	 */
	@Test
	public void testCopyOnWriteArrayList() {
		List<String> copyOnWriteArrayList = new CopyOnWriteArrayList();
//		List<String> copyOnWriteArrayList = new ArrayList<>();
		copyOnWriteArrayList.add("1");
		copyOnWriteArrayList.add("2");
		copyOnWriteArrayList.add("3");
		copyOnWriteArrayList.add("4");
		copyOnWriteArrayList.add("5");
		copyOnWriteArrayList.add("6");
		copyOnWriteArrayList.add("7");
		copyOnWriteArrayList.add("8");
		Iterator<String> iterator = copyOnWriteArrayList.iterator();
		while (iterator.hasNext()) {
			String s = null;
			if ("6".equals(s = iterator.next()))
				copyOnWriteArrayList.remove("6");
			System.out.println("s = " + s);
		}
	}

}
