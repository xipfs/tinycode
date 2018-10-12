package org.xipfs.lock;

import java.util.concurrent.atomic.AtomicReference;

/*
 ============================================================================
 Name       		: SpinLock.java
 Author      		: 0xC000005
 Date               : 2018年10月12日
 Version     	 	: 1.0
 Copyright   	: The MIT License (MIT)
 Description 	: 普通SpinLock (支持可重入）
 ============================================================================
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
