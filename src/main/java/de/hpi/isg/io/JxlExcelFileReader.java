package de.hpi.isg.io;

import jxl.Workbook;

import java.io.IOException;

/**
 * This class wraps the excel file reader functions from jxl library.
 *
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class JxlExcelFileReader extends ExcelFileReader {

    private Workbook workbook;

    public JxlExcelFileReader(final String filePath) {
        super(filePath);
    }

    @Override
    void digestExcelFile() throws IOException {

    }
}
