package com.build.report.configuration;


import com.build.report.model.Record;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CSVByteArrayItemReader implements ItemReader<Record> {
    private BufferedReader reader;
    private LineTokenizer tokenizer;
    private String[] headers;

    public void setFileContent(byte[] data) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8));
        this.tokenizer = new DelimitedLineTokenizer();
    }

    @Override
    public Record read() throws Exception {
        String line;
        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) continue; // Skip empty lines

            if (headers == null) {
                headers = line.split(",");
                ((DelimitedLineTokenizer) tokenizer).setNames(headers);
                continue; // Read next line after headers
            }

            try {
                FieldSet fieldSet = tokenizer.tokenize(line);
                return mapFieldSetToRecord(fieldSet);
            } catch (IncorrectTokenCountException e) {
                // Log and skip lines with incorrect token count
                System.err.println("Skipping line due to incorrect token count: " + line);
                continue;
            }
        }
        return null; // End of file
    }

    private Record mapFieldSetToRecord(FieldSet fieldSet) {
        Record record = new Record();
        record.setCode(fieldSet.readLong("code"));
        record.setDescription(fieldSet.readString("description"));
        record.setPurchaseTimePlanned(fieldSet.readInt("purchaseTimePlanned"));
        record.setCost(fieldSet.readDouble("cost"));
        record.setDemandForecast(fieldSet.readLong("demandForecast"));
        record.setDemandPLCDO(fieldSet.readInt("demandPLCDO"));
        record.setMin(fieldSet.readInt("min"));
        System.out.println("[INFO] Valid Record " + record);
        return record;
    }
}