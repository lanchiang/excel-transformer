package de.hpi.isg.exceptions;

/**
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class FormulaParseException extends Exception {

    private static final long serialVersionUID = -6110965409859807887L;

    public FormulaParseException(String message) {
        super(message);
    }

    public FormulaParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormulaParseException(Throwable cause) {
        super(cause);
    }

}
