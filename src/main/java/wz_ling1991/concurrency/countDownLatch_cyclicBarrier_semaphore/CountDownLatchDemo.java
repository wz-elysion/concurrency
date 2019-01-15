package wz_ling1991.concurrency.countDownLatch_cyclicBarrier_semaphore;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch:<br> 实现类似计数器的功能<br> 一个线程或多个线程等待其他线程运行达到某一目标后进行自己的下一步工作，
 * 而被等待的“其他线程”达到这个目标后继续自己下面的任务。
 *
 * @author weizheng
 */
public class CountDownLatchDemo {

  public static void main(String[] args) {
    CountDownLatch latch = new CountDownLatch(2);
    Runnable runnable = () -> {
      try {
        System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
        Thread.sleep(1000);
        System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
        latch.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };
    Thread t1 = new Thread(runnable);
    Thread t2 = new Thread(runnable);
    t1.start();
    t2.start();
    try {
      System.out.println("等待2个子线程执行完毕...");
      latch.await();//线程的start方法一定要放在此步之前！！！
      System.out.println("2个子线程已经执行完毕");
      System.out.println("继续执行主线程");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
