package de.hpi.isg.exceptions;

/**
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class ExcelFileReadingException extends Exception {

    private static final long serialVersionUID = -1119477227001691949L;

    public ExcelFileReadingException(String message) {
        super(message);
    }

    public ExcelFileReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelFileReadingException(Throwable cause) {
        super(cause);
    }

    public ExcelFileReadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
