package com.build.report.configuration;

import com.build.report.model.Record;
import com.build.report.repository.CsvRecordRepository;
import com.build.report.util.CsvRecordSkipPolicy;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    CsvRecordRepository csvRecordRepository;

    @Bean
    public Job processJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, JobExecutionListener listener) {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1(stepBuilderFactory))
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step1")
                .<Record, Record>chunk(10)
                .reader(itemReader(null))
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skipPolicy(new CsvRecordSkipPolicy())
                //   .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public ItemReader<Record> itemReader(byte[] fileContent) {
        return new CSVByteArrayItemReader();
    }

    @Bean
    public ItemProcessor<Record, Record> itemProcessor() {
        return record -> {
              CsvRecordSkipPolicy.validate(record);
            return record;
        };
    }

    @Bean
    public ItemWriter<Record> itemWriter() {
        return csvRecordRepository::saveAll;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("batch-task-");
        executor.initialize();
        return executor;
    }
}