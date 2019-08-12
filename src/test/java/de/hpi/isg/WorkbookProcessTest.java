package de.hpi.isg;

import de.hpi.isg.io.TheExcelFileReader;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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

            if (!excelFile.getName().equals("Businesses_Where_RV_less_than_12000.xls")) {
                continue;
            }

            System.out.println(excelFile.getName());
            TheExcelFileReader excelFileReader = new TheExcelFileReader(excelFile.getPath());
            Workbook workbook = null;
            try {
                workbook = excelFileReader.loadWorkBook();
            } catch (OldExcelFormatException e) {

            }

            if (workbook == null) {
                continue;
            }

            WorkbookProcessor workbookProcessor = new WorkbookProcessor(workbook);
            workbookProcessor.processWorkbook();
        }
    }
}
