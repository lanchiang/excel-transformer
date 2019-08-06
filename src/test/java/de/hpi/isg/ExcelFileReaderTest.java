package de.hpi.isg;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class ExcelFileReaderTest {

    @Test
    public void testLoadWorkbook() throws IOException {
        ExcelFileReader excelFileReader = new ExcelFileReader(getClass().getClassLoader().getResource("1-qtr-2011-directors.xls").getPath());
        Workbook workbook = excelFileReader.loadWorkBook();
        Assert.assertNotNull(workbook);
    }
}
