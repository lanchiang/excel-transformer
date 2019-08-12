package de.hpi.isg.io;

import java.io.IOException;

/**
 * The excel file reader for various libraries.
 *
 * @author Lan Jiang
 * @since 2019-08-12
 */
abstract public class ExcelFileReader {

    protected final String filePath;

    public ExcelFileReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * read and parse the file.
     *
     * @throws IOException
     */
    abstract void digestExcelFile() throws IOException;
}
