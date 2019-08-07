package de.hpi.isg;

import de.hpi.isg.io.ExcelFileReader;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class WorkbookProcessTest {

    @Test
    public void testProcessSheet() throws IOException {
        File[] inputExcels = new File("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk").listFiles();

        assert inputExcels != null;
        for (File excelFile : inputExcels) {
            ExcelFileReader excelFileReader = new ExcelFileReader(excelFile.getPath());
            Workbook workbook = excelFileReader.loadWorkBook();

            if (workbook == null) {
                continue;
            }

            WorkbookProcessor workbookProcessor = new WorkbookProcessor(workbook);
            workbookProcessor.processWorkbook();
        }

        ExcelFileReader excelFileReader = new ExcelFileReader(Objects.requireNonNull(getClass().getClassLoader().getResource("1-qtr-2011-directors.xls")).getPath());
        Workbook workbook = excelFileReader.loadWorkBook();
        WorkbookProcessor workbookProcessor = new WorkbookProcessor(workbook);
        workbookProcessor.processWorkbook();
    }
}
