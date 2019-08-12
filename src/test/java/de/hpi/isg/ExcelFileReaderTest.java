package de.hpi.isg;

import de.hpi.isg.io.TheExcelFileReader;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class ExcelFileReaderTest {

    @Test
    public void testLoadWorkbook() throws IOException {
        TheExcelFileReader excelFileReader = new TheExcelFileReader(Objects.requireNonNull(getClass().getClassLoader().getResource("1-qtr-2011-directors.xls")).getPath());
        Workbook workbook = excelFileReader.loadWorkBook();
        Assert.assertNotNull(workbook);
    }
}
