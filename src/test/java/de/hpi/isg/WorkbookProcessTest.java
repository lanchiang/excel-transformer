package de.hpi.isg;

import de.hpi.isg.exceptions.ExcelFileReadingException;
import de.hpi.isg.io.ExcelFileReader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class WorkbookProcessTest {

    @Test
    public void testProcessSheet() throws IOException, ExcelFileReadingException {
        File[] inputExcels = new File("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk").listFiles();

        File inputExcelFileFolder = new File("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk_converted");

        if (inputExcelFileFolder.delete()) {
            if (!inputExcelFileFolder.mkdir()) {
                return;
            }
        }

        assert inputExcels != null;
        for (File excelFile : inputExcels) {

//            if (!excelFile.getName().equals("remands-magistrates-court-tool-2016.xlsx")) {
//                continue;
//            }

            System.out.println(excelFile.getName());

            ExcelFileReader excelFileReader = new ExcelFileReader(excelFile.getPath());
            excelFileReader.digestExcelFile();
        }
    }
}
