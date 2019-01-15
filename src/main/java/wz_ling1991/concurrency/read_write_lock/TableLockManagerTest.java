package wz_ling1991.concurrency.read_write_lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TableLockManagerTest {

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			String is = String.valueOf(i);
			es.submit(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("reading a: " + is + "ready...");
						TableLockManager.lockWriteLock("a");
						System.out.println("reading a:" + is);
						Thread.sleep(1000);// 模拟读操作耗时
						System.out.println("reading a:" + is + "over");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						TableLockManager.unlockReadLock("a");
					}

				}
			});
		}

		for (int i = 0; i < 3; i++) {
			String is = String.valueOf(i);
			es.submit(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("writing a: " + is + "ready...");
						TableLockManager.lockWriteLock("a");
						System.out.println("writing a:" + is);

						Thread.sleep(1000);// 模拟写操作耗时
						System.out.println("writing a:" + is + "over");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						TableLockManager.unlockWriteLock("a");
					}
				}
			});
		}
		es.shutdown();
	}

}
