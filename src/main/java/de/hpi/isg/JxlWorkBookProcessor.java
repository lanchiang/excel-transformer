package de.hpi.isg;

import de.hpi.isg.concept.WrappedSheet;
import jxl.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class JxlWorkBookProcessor extends WorkBookProcessor {

    private final Workbook workbook;

    private WrappedSheet wrappedSheet;

    public JxlWorkBookProcessor(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public void process() {
        int numOfSheets = workbook.getNumberOfSheets();

        Sheet[] sheets = workbook.getSheets();

        for (int i = 0; i < numOfSheets; i++) {
            Sheet sheet = workbook.getSheets()[i];

            System.out.println(sheet.getName());


            List<List<String>> curatedSheetData = processSheet(sheet);
            wrappedSheet = new WrappedSheet(sheet.getName(), curatedSheetData);
        }
    }

    private List<List<String>> processSheet(final Sheet sheet) {
        List<List<String>> curatedSheet = new LinkedList<>();

        int numOfRows = sheet.getRows();
        int numOfColumns = sheet.getColumns();

        for (int i = 0; i < numOfRows; i++) {
            List<String> curatedRow = new LinkedList<>();
            for (int j = 0; j < numOfColumns; j++) {
                Cell cell = sheet.getCell(j, i);
                CellType cellType = cell.getType();
                curatedRow.add(cell.getContents());
            }
            System.out.println(curatedRow);
        }

        return curatedSheet;
    }
}
