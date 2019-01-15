package wz_ling1991.concurrency.countDownLatch_cyclicBarrier_semaphore;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier:<br> 字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。<br> 叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。
 *
 * @author weizheng
 */
public class CyclicBarrierDemo {

  public static void main(String[] args) {
    int N = 3;
    CyclicBarrier barrier = new CyclicBarrier(N, () -> System.out
        .println("所有线程执行完了，随机选择一个线程，执行该段代码。当前线程为：" + Thread.currentThread().getName()));
    for (int i = 0; i < N; i++) {
      new Writer(barrier).start();
    }
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("CyclicBarrier重用");
    for (int i = 0; i < N; i++) {
      new Writer(barrier).start();
    }
  }
}

class Writer extends Thread {

  private CyclicBarrier cyclicBarrier;

  public Writer(CyclicBarrier cyclicBarrier) {
    this.cyclicBarrier = cyclicBarrier;
  }

  @Override
  public void run() {
    System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
    try {
      Thread.sleep(1000); // 以睡眠来模拟写入数据操作
      System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
      cyclicBarrier.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
    System.out.println(Thread.currentThread().getName() + "所有线程写入完毕，继续处理其他任务...");
  }
}
