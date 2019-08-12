package de.hpi.isg;

import de.hpi.isg.exceptions.FormulaParseException;
import de.hpi.isg.io.ExcelFileReader;
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

        File inputExcelFileFolder = new File("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk_converted");

        if (inputExcelFileFolder.delete()) {
            if (!inputExcelFileFolder.mkdir()) {
                return;
            }
        }

        assert inputExcels != null;
        for (File excelFile : inputExcels) {

//            if (!excelFile.getName().equals("bis-13-1262-long-run-income-elasticities-DATA.xls.xlsx")) {
//                continue;
//            }

            System.out.println(excelFile.getName());

            ExcelFileReader excelFileReader = new ExcelFileReader(excelFile.getPath());
            excelFileReader.digestExcelFile();
        }
    }
}
