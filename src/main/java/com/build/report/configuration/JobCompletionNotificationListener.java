package com.build.report.configuration;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("batch started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("batch complted");
    }
}
