package com.spring.code.samples.controllers;

import com.spring.code.samples.services.LockTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/locktable")
public class LockTableController {

    @Autowired
    private LockTableService lockTableService;

    @GetMapping(path = "/read")
    public ResponseEntity testLockReadTable() {
        this.lockTableService.getAndLockRead();
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/write")
    public ResponseEntity testLockWriteTable() {
        this.lockTableService.getAndLockWrite();
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/2")
    public ResponseEntity testLockTable2() {
        this.lockTableService.getAndLock2();
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/3")
    public ResponseEntity testLockTable3() {
        this.lockTableService.getAndLock3();
        return ResponseEntity.ok().build();
    }

}
