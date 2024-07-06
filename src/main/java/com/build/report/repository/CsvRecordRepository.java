package com.build.report.repository;

import com.build.report.model.CsvRecordStatistics;
import com.build.report.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CsvRecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT COUNT(c) AS recordCount, MAX(c.cost) AS maxCost, AVG(c.purchaseTimePlanned) AS avgPurchaseTimePlanned FROM Record c")
    CsvRecordStatistics getCsvRecordStatistics();
}
