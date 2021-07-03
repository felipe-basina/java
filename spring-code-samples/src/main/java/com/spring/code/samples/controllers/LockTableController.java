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

    @GetMapping
    public ResponseEntity testLockTable() {
        this.lockTableService.getAndLock();
        return ResponseEntity.ok().build();
    }

}
