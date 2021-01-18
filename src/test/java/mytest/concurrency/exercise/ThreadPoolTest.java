package mytest.concurrency.exercise;

import org.junit.Test;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

	@Test
	public void testThreadPoolCode() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				5, 10, 2, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10),
				new DefaultManagedAwareThreadFactory(), new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				System.out.println(Thread.currentThread().getName() + " reject");
			}
		});

		for (int i = 0; i < 100; i++) {
			final int k = i;
			threadPoolExecutor.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					System.out.println(Thread.currentThread().getName() + " -start- " + k);
					Thread.sleep(4000);
					System.out.println(Thread.currentThread().getName() + " -end- " + k);
					return String.valueOf(k);
				}
			});
		}

		while (true) {

		}
	}


	private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
	private static final int COUNT_BITS = Integer.SIZE - 3;
	private static final int CAPACITY = (1 << COUNT_BITS) - 1;

	// runState is stored in the high-order bits
	private static final int RUNNING = -1 << COUNT_BITS;
	private static final int SHUTDOWN = 0 << COUNT_BITS;
	private static final int STOP = 1 << COUNT_BITS;
	private static final int TIDYING = 2 << COUNT_BITS;
	private static final int TERMINATED = 3 << COUNT_BITS;

	// Packing and unpacking ctl
	private static int runStateOf(int c) {
		return c & ~CAPACITY;
	}

	private static int workerCountOf(int c) {
		return c & CAPACITY;
	}

	private static int ctlOf(int rs, int wc) {
		return rs | wc;
	}

	@Test
	public void simpleTest() {
		int running = -1 << 29;
		System.out.println(ctl + " --  ctl  -- " + Integer.toBinaryString(ctl.get()));
		System.out.println(COUNT_BITS + " --  COUNT_BITS  -- " + Integer.toBinaryString(COUNT_BITS));
		System.out.println(CAPACITY + " --  CAPACITY  -- " + Integer.toBinaryString(CAPACITY));
		System.out.println(RUNNING + " --  RUNNING  -- " + Integer.toBinaryString(RUNNING));
		System.out.println(SHUTDOWN + " --  SHUTDOWN  -- " + Integer.toBinaryString(SHUTDOWN));
		System.out.println(STOP + " --  STOP  -- " + Integer.toBinaryString(STOP));
		System.out.println(TIDYING + " --  TIDYING  -- " + Integer.toBinaryString(TIDYING));
		System.out.println(TERMINATED + " --  TERMINATED  -- " + Integer.toBinaryString(TERMINATED));
		int workerCount = workerCountOf(ctl.get());
		System.out.println(workerCount + " --  workerCountOf  -- " + Integer.toBinaryString(workerCount));
		int runStateOf = runStateOf(ctl.get());
		System.out.println(runStateOf + " --  runStateOf  -- " + Integer.toBinaryString(runStateOf));
		System.out.println(~CAPACITY + " --  ~CAPACITY  -- " + Integer.toBinaryString(~CAPACITY));

	}

}
