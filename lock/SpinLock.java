import java.util.concurrent.atomic.AtomicReference;

/**
普通SpinLock (支持可重入）
*/

public class SpinLock {
	// use thread itself as synchronization state
	private AtomicReference<Thread> owner = new AtomicReference<Thread>();
	// reentrant count of a thread, no need to be volatile
	private int count = 0; 

	public void lock() {
		Thread t = Thread.currentThread();
		if (t == owner.get()) { 
			// if re-enter, increment the count.
			++count;
			return;
		}
		// spin
		while (owner.compareAndSet(null, t)) {
		} 
	}

	public void unlock() {
		Thread t = Thread.currentThread();
		// only the owner could do unlock;
		if (t == owner.get()) { 
			// reentrant count not zero, just decrease the counter.
			if (count > 0)
				--count; 
			else {
				// compareAndSet is not need here, already checked
				owner.set(null);
			}
		}
	}
}
