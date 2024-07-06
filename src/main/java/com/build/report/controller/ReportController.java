package com.build.report.controller;

import com.build.report.configuration.CSVByteArrayItemReader;
import com.build.report.service.ExcelGeneratorService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/batch")
public class ReportController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    private CSVByteArrayItemReader itemReader;

    @Autowired
    private ExcelGeneratorService excelGenerator;

    @Autowired
    public ReportController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/downloadExcelReport")
    public ResponseEntity<InputStreamResource> downloadExcelReport() throws IOException {
        ByteArrayInputStream in = excelGenerator.generateExcelReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reports.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            itemReader.setFileContent(file.getBytes());

            jobLauncher.run(job, jobParameters);

            return new ResponseEntity<>("File processed successfully", HttpStatus.OK);
        } catch (IOException | JobExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process file: " + e.getMessage());
        }
    }
}