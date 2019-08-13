package de.hpi.isg.io;

import de.hpi.isg.PoiWorkBookProcessor;
import de.hpi.isg.concept.WrappedSheet;
import de.hpi.isg.exceptions.ExcelFileReadingException;
import lombok.Getter;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * The excel file reader for various libraries.
 *
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class ExcelFileReader {

    protected final String filePath;

    @Getter
    private Collection<WrappedSheet> wrappedSheets;

    public ExcelFileReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * read and parse the file.
     *
     * @throws IOException
     */
    public void digestExcelFile() throws IOException, ExcelFileReadingException {
        File file = new File(filePath);

        Workbook poiWorkBook;
        try {
            poiWorkBook = WorkbookFactory.create(file);
        } catch (OldExcelFormatException | NotOLE2FileException | POIXMLException e) {
            throw new ExcelFileReadingException(file.getName(), e);
        }

        if (poiWorkBook != null) {
            // the file can be digested by the apache poi library.
            PoiWorkBookProcessor processor = new PoiWorkBookProcessor(poiWorkBook);
            processor.process();

            this.wrappedSheets = processor.getWrappedSheets();

            poiWorkBook.close();
        }
    }

    public static void main(String[] args) throws IOException, ExcelFileReadingException {
        ExcelFileReader excelFileReader = new ExcelFileReader("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk/Businesses_Where_RV_less_than_12000.xls");
//        ExcelFileReader excelFileReader = new ExcelFileReader("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk/1-qtr-2011-directors.xls");
        excelFileReader.digestExcelFile();
    }
}
