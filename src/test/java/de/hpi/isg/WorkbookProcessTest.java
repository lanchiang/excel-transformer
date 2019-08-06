package de.hpi.isg;

import de.hpi.isg.io.ExcelFileReader;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class WorkbookProcessTest {

    @Test
    public void testProcessSheet() throws IOException {
        ExcelFileReader excelFileReader = new ExcelFileReader(Objects.requireNonNull(getClass().getClassLoader().getResource("1-qtr-2011-directors.xls")).getPath());
        Workbook workbook = excelFileReader.loadWorkBook();
        WorkbookProcessor workbookProcessor = new WorkbookProcessor(workbook);
        workbookProcessor.processWorkbook();
    }
}
