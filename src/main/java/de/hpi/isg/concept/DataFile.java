package de.hpi.isg.concept;

import lombok.Getter;

/**
 * A data file is a delimiter-separated file that represents the content of an excel file. The value of each cell in the
 * excel file is placed in the corresponding position in the data file.
 *
 * @author Lan Jiang
 * @since 2019-08-09
 */
public class DataFile {

    @Getter
    private int numOfColumns;

    @Getter
    private int numOfRows;

    @Getter
    private String[][] content;

    public DataFile(int numOfColumns, int numOfRows) {
        this.numOfColumns = numOfColumns;
        this.numOfRows = numOfRows;

        content = new String[numOfRows][];
        for (int i = 0; i < numOfRows; i++) {
            content[i] = new String[numOfColumns];
        }
    }

}
