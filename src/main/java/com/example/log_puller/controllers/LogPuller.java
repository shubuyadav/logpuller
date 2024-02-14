package com.example.log_puller.controllers;

import com.example.log_puller.modal.Log;
import com.example.log_puller.modal.LogSearchInput;
import com.example.log_puller.serviceimpl.LogPullerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/LogPuller")
public class LogPuller {

    LogPullerService logPullerService;
    public LogPuller(LogPullerService logPullerService) {
        this.logPullerService = logPullerService;
    }
    @GetMapping(value = "/fetchLogs", produces = "application/json")
    public List<Log> fetchLogs(@RequestBody LogSearchInput logSearchInput) {

        return logPullerService.fetchLogs(logSearchInput);
    }
}
