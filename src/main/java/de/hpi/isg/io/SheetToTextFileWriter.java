package de.hpi.isg.io;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class SheetToTextFileWriter {

    private final CSVWriter csvWriter;

    public SheetToTextFileWriter(String filePath) throws IOException {
        filePath = "/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk_converted/" + filePath;

        if (!filePath.endsWith(".csv")) {
            filePath = filePath + ".csv";
        }
        csvWriter = new CSVWriter(new FileWriter(filePath));
    }

    public void writeLine(List<String> curatedRow) {
        String[] curatedRowArray = curatedRow.toArray(new String[0]);
        csvWriter.writeNext(curatedRowArray);
    }

    public void close() throws IOException {
        csvWriter.close();
    }
}
