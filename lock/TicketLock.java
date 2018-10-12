package org.xipfs.lock;
/*
 ============================================================================
 Name       		: TicketLock.java
 Author      		: 0xC000005
 Date               : 2018年10月12日
 Version     	 	: 1.0
 Copyright   	: The MIT License (MIT)
 Description 	: 基于排队的公平自旋锁
 ============================================================================
 */

import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock {
	
    //当前正在接受服务的号码
    private AtomicInteger serviceNum = new AtomicInteger(0);

    //希望得到服务的排队号码
    private AtomicInteger ticketNum  = new AtomicInteger(0);

    /**
     * 尝试获取锁
     * @return
     */
    public int lock() {
        // 获取排队号
        int acquiredTicketNum = ticketNum.getAndIncrement();
        // 当排队号不等于服务号的时候开始自旋等待
        while (acquiredTicketNum != serviceNum.get()) {
        }
        return acquiredTicketNum;
    }
    /**
     * 释放锁
     * @param ticketNum
     */
    public void unlock(int ticketNum) {
        // 服务号增加，准备服务下一位
        int nextServiceNum = serviceNum.get() + 1;
        // 只有当前线程拥有者才能释放锁
        serviceNum.compareAndSet(ticketNum, nextServiceNum);
    }
}
