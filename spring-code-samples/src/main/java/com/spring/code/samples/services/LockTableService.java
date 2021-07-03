package com.spring.code.samples.services;

import com.spring.code.samples.dao.LockTableDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LockTableService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LockTableDao lockTableDao;

    public void getAndLock() {
        int total = 3;
        List<Integer> integerList = IntStream.rangeClosed(1, total)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = new ForkJoinPool(total);
        forkJoinPool.execute(() -> {
            integerList.parallelStream().forEach(index -> {
                LOGGER.info(String.format("returned e=%s", this.lockTableDao.getAndLock()));
            });
        });
    }

}
