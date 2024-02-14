package com.example.log_puller.modal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Log {

    private String fileName;
    private String logData;
    private int lineNumber;

    public Log(String fileName, String logData, int lineNumber) {
        this.fileName = fileName;
        this.logData = logData;
        this.lineNumber = lineNumber;
    }
}
