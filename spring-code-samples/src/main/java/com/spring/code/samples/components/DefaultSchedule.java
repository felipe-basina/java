package com.spring.code.samples.components;

import com.spring.code.samples.dao.LockTableDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DefaultSchedule {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LockTableDao lockTableDao;

    //@Scheduled(fixedDelay = 2000)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void schec01() {
        this.lockTableDao.getAndLockRead();
        LOGGER.info(String.format("Running at %s", LocalDateTime.now()));
    }

}
