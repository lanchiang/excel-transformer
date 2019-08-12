package de.hpi.isg.concept;

import lombok.Getter;

import java.util.List;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class WrappedSheet {

    @Getter
    private final String sheetFileName;

    @Getter
    private final List<List<String>> curatedSheetData;

    public WrappedSheet(String sheetFileName, List<List<String>> curatedSheetData) {
        this.sheetFileName = sheetFileName;
        this.curatedSheetData = curatedSheetData;
    }
}
