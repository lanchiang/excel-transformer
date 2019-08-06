package de.hpi.isg;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class ExcelFileReader {

    private final String filePath;

    public ExcelFileReader(String filePath) {
        this.filePath = filePath;
    }

    public Workbook loadWorkBook() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        return workbook;
    }
}
