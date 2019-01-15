package wz_ling1991.concurrency.countDownLatch_cyclicBarrier_semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

	public static void main(String[] args) {
		int N = 3; // 工人数
		ExecutorService es = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(2); // 机器数目
		for (int i = 0; i < N; i++) {
			es.submit(new Worker(i, semaphore));
		}
		es.shutdown();
	}

}

class Worker extends Thread {
	private int num;
	private Semaphore semaphore;

	public Worker(int num, Semaphore semaphore) {
		this.num = num;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		try {
			semaphore.acquire();
			System.out.println("工人" + this.num + "占用一个机器在生产...");
			Thread.sleep(2000);
			System.out.println("工人" + this.num + "释放出机器");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
