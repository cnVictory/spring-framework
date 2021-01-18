package mytest.concurrency;

import org.junit.Test;

/**
 * @BelongsProject: demo
 * @BelongsPackage: com.example.demo
 * @Author: Lovezly
 * @CreateTime: 2020-09-02 06:41
 * @Description:
 */
public class SimpleThreadTest {

    @Test
    public void test1() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("test1 ~~~");
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("test2 ~~~");
                }
            }
        });

        System.out.println("t1 state = " + t1.getState());
        System.out.println("t2 state = " + t2.getState());

        t1.start();
        t2.start();
    }

    /**
     * 测试一下
     */
    @Test
    public void testYield() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("test1 ~~~" + Thread.currentThread().getState());
                    if (i == 5) {
                        Thread.yield();
                        System.out.println("t1 state = " + Thread.currentThread().getState());
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("test2 ~~~" + Thread.currentThread().getState());
                    if (i == 3) {
                        Thread.yield();
                        System.out.println("t2 state = " + Thread.currentThread().getState());
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void testJoin() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("test1 ~~~");
                    if (i == 5) {
                        Thread.yield();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("test2 ~~~");
                    if (i == 3) {
                        try {
                            t1.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void testSynchronizedReentrant() {
        syn1();
    }

    private synchronized void syn1() {
        for (int i = 0; i < 10; i++) {
            System.out.println("~~~~~~~ syn 1 ~~~~~~~~");
            syn2();
        }
    }

    private synchronized void syn2() {
        for (int i = 0; i < 3; i++) {
            System.out.println("~~~~~~~ syn 2 ~~~~~~~~");
        }
    }

    @Test
    public void testSynchronizedReentrant2() {
        Child child = new Child();
        child.method();
    }

    private class Parent{

        synchronized void method() {
            System.out.println("~~~ parent ~~~");
        }
    }

    private class Child extends Parent{
        @Override
        synchronized void method() {
            super.method();
            System.out.println("~~~ child ~~~");
        }
    }

	@Test
	public void test2() {

		// Constants shared across ForkJoinPool and WorkQueue

		// 限定参数
		final int SMASK = 0xffff;        //  低位掩码，也是最大索引位
		final int MAX_CAP = 0x7fff;        //  工作线程最大容量
		final int EVENMASK = 0xfffe;        //  偶数低位掩码
		final int SQMASK = 0x007e;        //  workQueues 数组最多64个槽位

		// ctl 子域和 WorkQueue.scanState 的掩码和标志位
		final int SCANNING = 1;             // 标记是否正在运行任务
		final int INACTIVE = 1 << 31;       // 失活状态  负数
		final int SS_SEQ = 1 << 16;       // 版本戳，防止ABA问题

		// ForkJoinPool.config 和 WorkQueue.config 的配置信息标记
		final int MODE_MASK = 0xffff << 16;  // 模式掩码
		final int LIFO_QUEUE = 0; //LIFO队列
		final int FIFO_QUEUE = 1 << 16;//FIFO队列
		final int SHARED_QUEUE = 1 << 31;       // 共享模式队列，负数

		System.out.println("SMASK = " + Integer.toBinaryString(SMASK));
		System.out.println("MAX_CAP = " + Integer.toBinaryString(MAX_CAP));
		System.out.println("EVENMASK = " + Integer.toBinaryString(EVENMASK));
		System.out.println("SQMASK = " + Integer.toBinaryString(SQMASK));
		System.out.println("SCANNING = " + Integer.toBinaryString(SCANNING));
		System.out.println("INACTIVE = " + Integer.toBinaryString(INACTIVE));
		System.out.println("SS_SEQ = " + Integer.toBinaryString(SS_SEQ));
		System.out.println("MODE_MASK = " + Integer.toBinaryString(MODE_MASK));
		System.out.println("LIFO_QUEUE = " + Integer.toBinaryString(LIFO_QUEUE));
		System.out.println("FIFO_QUEUE = " + Integer.toBinaryString(FIFO_QUEUE));
		System.out.println("SHARED_QUEUE = " + Integer.toBinaryString(SHARED_QUEUE));
	}
}
