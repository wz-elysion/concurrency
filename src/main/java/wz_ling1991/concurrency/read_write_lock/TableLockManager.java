package wz_ling1991.concurrency.read_write_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 模拟实现表读写锁机制。<br>
 * 使用时只需调用对应的锁方法，用完后调用释放锁方法即可！<br>
 * 读读共享、读写互斥、写写互斥，ReentrantReadWriteLock该类内部已经实现。
 * @author weizheng
 *
 */
public class TableLockManager {

	private static Map<String, ReadWriteLock> tableLockMap = new HashMap<>();

	/**
	 * 单例，禁止实例化
	 */
	private TableLockManager() {
	}
	
	/**
	 * 写加锁
	 * @param tableName
	 */
	public static void lockWriteLock(String tableName) {
		initLock(tableName);
		tableLockMap.get(tableName.toLowerCase()).writeLock().lock();
	}
	
	/**
	 * 读加锁
	 * @param tableName
	 */
	public static void lockReadLock(String tableName) {
		initLock(tableName);
		tableLockMap.get(tableName.toLowerCase()).readLock().lock();
	}
	
	/**
	 * 释放写锁
	 * @param tableName
	 */
	public static void unlockWriteLock(String tableName) {
		initLock(tableName);
		tableLockMap.get(tableName.toLowerCase()).writeLock().unlock();
	}
	
	/**
	 * 释放读锁
	 * @param tableName
	 */
	public static void unlockReadLock(String tableName) {
		initLock(tableName);
		tableLockMap.get(tableName.toLowerCase()).writeLock().unlock();
	}

	private static void initLock(String tableName) {
		if (tableLockMap.get(tableName.toLowerCase()) == null) {
			synchronized (tableLockMap) {// 二次判断，保证同一个表的锁只往map里面put一次。
				if (tableLockMap.get(tableName.toLowerCase()) == null) {
					tableLockMap.put(tableName.toLowerCase(), new ReentrantReadWriteLock());
				}
			}
		}
	}

}
