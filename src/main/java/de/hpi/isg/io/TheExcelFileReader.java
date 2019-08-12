package de.hpi.isg.io;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class TheExcelFileReader {

    private final String filePath;

    public TheExcelFileReader(String filePath) {
        this.filePath = filePath;
    }

    public Workbook loadWorkBook() throws IOException, OldExcelFormatException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        return workbook;
    }
}
