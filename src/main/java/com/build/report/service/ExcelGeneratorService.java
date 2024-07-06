package com.build.report.service;

import com.build.report.model.CsvRecordStatistics;
import com.build.report.repository.CsvRecordRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ExcelGeneratorService {

    @Autowired
    private CsvRecordRepository repository;

    public ByteArrayInputStream generateExcelReport() throws IOException {

        CsvRecordStatistics csvRecordStatistics = repository.getCsvRecordStatistics();

        String[] columns = {"RecordCount", "MaxCost", "AvgPurchaseTimePlanned"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reports");

            // Header Row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
            }

            // Data Row
            Row row = sheet.createRow(1);
            row.createCell(1).setCellValue(csvRecordStatistics.getRecordCount());
            row.createCell(2).setCellValue(csvRecordStatistics.getMaxCost());
            row.createCell(3).setCellValue(csvRecordStatistics.getAvgPurchaseTimePlanned());

            workbook.write(out);
            workbook.close();
            out.close();
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
