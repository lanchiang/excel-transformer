package de.hpi.isg.io;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

/**
 * This class wraps the excel file reader functions from apache poi library.
 *
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class PoiExcelFileReader extends ExcelFileReader {

    private Workbook workbook;

    public PoiExcelFileReader(final String filePath) {
        super(filePath);
    }

    @Override
    void digestExcelFile() throws IOException {

    }
}
