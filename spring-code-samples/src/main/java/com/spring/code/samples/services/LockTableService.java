package com.spring.code.samples.services;

import com.spring.code.samples.dao.LockTableDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LockTableService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LockTableDao lockTableDao;

    public void getAndLockRead() {
        int total = 3;
        List<Integer> integerList = IntStream.rangeClosed(1, total)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = new ForkJoinPool(total);
        forkJoinPool.execute(() -> {
            integerList.parallelStream().forEach(index -> {
                LOGGER.info(String.format("returned e=%s", this.lockTableDao.getAndLockRead()));
            });
        });
    }

    public void getAndLockWrite() {
        int total = 3;
        List<Integer> integerList = IntStream.rangeClosed(1, total)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = new ForkJoinPool(total);
        forkJoinPool.execute(() -> {
            integerList.parallelStream().forEach(index -> {
                LOGGER.info(String.format("returned e=%s", this.lockTableDao.getAndLockWrite()));
            });
        });
    }

    public void getAndLock2() {
        int total = 3;
        List<Integer> integerList = IntStream.rangeClosed(1, total)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = new ForkJoinPool(total);
        forkJoinPool.execute(() -> {
            integerList.parallelStream().forEach(index -> {
                LOGGER.info(String.format("returned e=%s", this.lockTableDao.getAndLock3()));
            });
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void getAndLock3() {
        int total = 3;
        List<Integer> integerList = IntStream.rangeClosed(1, total)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = new ForkJoinPool(total);
        forkJoinPool.execute(() -> {
            integerList.parallelStream().forEach(index -> {
                LOGGER.info(String.format("returned e=%s", this.lockTableDao.getAndLock5()));
            });
        });
    }

}
