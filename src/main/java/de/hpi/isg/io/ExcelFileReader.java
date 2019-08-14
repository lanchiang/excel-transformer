package de.hpi.isg.io;

import de.hpi.isg.PoiWorkBookProcessor;
import de.hpi.isg.concept.WrappedSheet;
import de.hpi.isg.exceptions.ExcelFileReadingException;
import lombok.Getter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
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
        } catch (OldExcelFormatException | NotOLE2FileException | POIXMLException | EmptyFileException e) {
            throw new ExcelFileReadingException(file.getName(), e);
        }

        if (poiWorkBook != null) {
            // the file can be digested by the apache poi library.
            PoiWorkBookProcessor processor = new PoiWorkBookProcessor(poiWorkBook);
            processor.process();

            this.wrappedSheets = processor.getWrappedSheets();

            try {
                poiWorkBook.close();
            } catch (Exception e) {
                throw new ExcelFileReadingException(file.getName(), e);
            }
        }
    }
}
