package com.epam.util;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SingleRWLock {
    public static final ReadWriteLock INSTANCE = new ReentrantReadWriteLock();
}