package de.hpi.isg.io;

import com.opencsv.CSVWriter;
import de.hpi.isg.concept.WrappedSheet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This writer write each time a line of excel cells from the excel file into the corresponding CSV file line.
 *
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class SheetToTextFileWriter {

    private final CSVWriter csvWriter;

    public SheetToTextFileWriter(String filePath) throws IOException {
        if (!filePath.endsWith(".csv")) {
            filePath = filePath + ".csv";
        }

        String outputFilePath = filePath;
        csvWriter = new CSVWriter(new FileWriter(outputFilePath));
    }

    public void write(WrappedSheet wrappedSheet) throws IOException {
        for (List<String> curatedRow : wrappedSheet.getCuratedSheetData()) {
            writeLine(curatedRow);
        }
        close();
    }

    private void writeLine(List<String> curatedRow) {
        String[] curatedRowArray = curatedRow.toArray(new String[0]);
        csvWriter.writeNext(curatedRowArray);
    }

    private void close() throws IOException {
        csvWriter.close();
    }
}
