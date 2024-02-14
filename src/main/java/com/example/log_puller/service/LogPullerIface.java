package com.example.log_puller.service;

import com.example.log_puller.modal.Log;
import com.example.log_puller.modal.LogSearchInput;

import java.util.List;

public interface LogPullerIface {
    List<Log> fetchLogs(LogSearchInput logSearchInput);
}
