package de.hpi.isg.io;

import jxl.read.biff.BiffException;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

/**
 * The excel file reader for various libraries.
 *
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class ExcelFileReader {

    protected final String filePath;

    public ExcelFileReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * read and parse the file.
     *
     * @throws IOException
     */
    public void digestExcelFile() throws IOException {
        Workbook poiWorkBook = null;
        jxl.Workbook jxlWorkBook = null;
        try {
            poiWorkBook = WorkbookFactory.create(new File(filePath));
        } catch (OldExcelFormatException e) {
            try {
                e.printStackTrace();
                jxlWorkBook = jxl.Workbook.getWorkbook(new File(filePath));
            } catch (BiffException ex) {
                ex.printStackTrace();
            }
        }

        if (poiWorkBook != null) {
            // the file can be digested by the apache poi library.
        }
        else {
            if (jxlWorkBook != null) {
                // the file can not be digested by the apache poi library, but can by the jxl library.
            }
            else {
                throw new RuntimeException("The excel file cannot be digested.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ExcelFileReader excelFileReader = new ExcelFileReader("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk/Businesses_Where_RV_less_than_12000.xls");
        excelFileReader.digestExcelFile();
    }
}
