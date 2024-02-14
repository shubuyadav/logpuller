package com.example.log_puller.serviceimpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.log_puller.modal.Log;
import com.example.log_puller.modal.LogSearchInput;
import com.example.log_puller.service.LogPullerIface;
import com.example.log_puller.utility.PropertyReader;
import com.example.log_puller.utility.S3ClientConfiguration;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LogPullerService implements LogPullerIface {

    private final S3ClientConfiguration s3ClientConfiguration;
    private final PropertyReader propertyReader;

    public LogPullerService(S3ClientConfiguration s3ClientConfiguration, PropertyReader propertyReader) {
        this.s3ClientConfiguration = s3ClientConfiguration;
        this.propertyReader = propertyReader;
    }

    @Override
    public List<Log> fetchLogs(LogSearchInput logSearchInput) {

        String bucketName = propertyReader.getProperty("bucket.name");
        AmazonS3 s3Client = s3ClientConfiguration.createS3Client();
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);

        return fetch_ParseLogs(s3Client, bucketName, logSearchInput, req);
    }

    private List<Log> fetch_ParseLogs(AmazonS3 s3Client, String bucketName, LogSearchInput logSearchInput, ListObjectsV2Request req) {

        ListObjectsV2Result result;
        Date startDate = logSearchInput.getStartDate();
        Date endDate = logSearchInput.getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Log> logs = new ArrayList<>();

        do {
            result = s3Client.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String key = objectSummary.getKey();
                String directoryName = key.substring(0, 10);
                Date directoryDate;
                try {
                    directoryDate = dateFormat.parse(directoryName);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (isDateBetween(directoryDate, startDate, endDate)) {
                    S3Object object = s3Client.getObject(bucketName, key);
                    try (S3ObjectInputStream objectContent = object.getObjectContent()) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(objectContent));
                        String line;
                        int lineNumber = 1;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains(logSearchInput.getSearchString())) {
                                logs.add(new Log(key, line, lineNumber));
                            }
                            lineNumber++;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            req.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());

        return logs;
    }

    //Ignore timestamp in the date, though it can be added later as per requirement.
    //Joda-time library is a better option here.
    private boolean isDateBetween(Date dateToCheck, Date startDate, Date endDate) {
        LocalDate date = dateToCheck.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return !date.isBefore(start) && !date.isAfter(end);
    }
}
