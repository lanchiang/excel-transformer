package de.hpi.isg.io;

import de.hpi.isg.JxlWorkBookProcessor;
import de.hpi.isg.PoiWorkBookProcessor;
import de.hpi.isg.WorkBookProcessor;
import de.hpi.isg.concept.WrappedSheet;
import de.hpi.isg.exceptions.FormulaParseException;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        File file = new File(filePath);

        Workbook poiWorkBook = null;
        jxl.Workbook jxlWorkBook = null;

        try {
            poiWorkBook = WorkbookFactory.create(file);
        } catch (OldExcelFormatException | NotOLE2FileException e) {
//            try {
                e.printStackTrace();
//                jxlWorkBook = jxl.Workbook.getWorkbook(file);
                System.out.println(file.getName());
//            } catch (BiffException ex) {
//                ex.printStackTrace();
//            }
        }

//        try {
//            jxlWorkBook = jxl.Workbook.getWorkbook(new File(filePath));
//        } catch (BiffException e) {
//            e.printStackTrace();
//        }

        if (poiWorkBook != null) {
            // the file can be digested by the apache poi library.
            PoiWorkBookProcessor processor = new PoiWorkBookProcessor(poiWorkBook);
            processor.process();

            for (WrappedSheet wrappedSheet : processor.getWrappedSheets()) {
                String outputFileName = file.getName() + "." + wrappedSheet.getSheetFileName();
                try {
                    SheetToTextFileWriter writer = new SheetToTextFileWriter(outputFileName);
                    for (List<String> curatedRow : wrappedSheet.getCuratedSheetData()) {
                        writer.writeLine(curatedRow);
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            poiWorkBook.close();
        }
        else {
            if (jxlWorkBook != null) {
                // the file can not be digested by the apache poi library, but can by the jxl library.
                WorkBookProcessor processor = new JxlWorkBookProcessor(jxlWorkBook);
                processor.process();
            }
            else {
//                throw new RuntimeException("The excel file cannot be digested.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ExcelFileReader excelFileReader = new ExcelFileReader("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk/Businesses_Where_RV_less_than_12000.xls");
//        ExcelFileReader excelFileReader = new ExcelFileReader("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk/1-qtr-2011-directors.xls");
        excelFileReader.digestExcelFile();
    }
}
