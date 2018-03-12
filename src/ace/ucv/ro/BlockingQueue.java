package ace.ucv.ro;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
	private volatile List<Object> queue;
	private final int limit;
	private Lock lock;
	
	public BlockingQueue(int limit) {
		this.queue = new LinkedList<>();
		this.limit = limit;
		this.lock = new ReentrantLock();
	}
	
	public void enqueue(Object object) throws InterruptedException {
		lock.lock();
		while(queue.size() == limit) {
			lock.wait();
		}
		
		queue.add(object);
		lock.notify();
	}
	
	public void dequeue(Object object) throws InterruptedException {
		lock.lock();
		while(queue.isEmpty()) {
			lock.wait();
		}
		
		queue.remove(object);
		lock.notify();
	}
}
