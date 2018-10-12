package org.xipfs.lock;
/*
 ============================================================================
 Name       		: SimpleSpinLock.java
 Author      		: 0xC000005
 Date               : 2018年10月12日
 Version     	 	: 1.0
 Copyright   	: The MIT License (MIT)
 Description 	: 非公平自旋锁
 ============================================================================
 */

import java.util.concurrent.atomic.AtomicReference;

public class SimpleSpinLock {
    //维护当前拥有锁的线程对象
    private AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        Thread currentThread = Thread.currentThread();
        // 只有owner没有被加锁的时候，才能够加锁成功，否则自旋等待
        while (!owner.compareAndSet(null, currentThread)) {
        }
    }

    public void unlock() {
        Thread currentThread = Thread.currentThread();
        // 只有锁的owner才能够释放锁，其它的线程因为无法满足Compare，因此不会Set成功
        owner.compareAndSet(currentThread, null);
    }
}
