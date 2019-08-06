package de.hpi.isg;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Lan Jiang
 * @since 2019-08-06
 */
public class WorkbookProcessor {

    private final Workbook workbook;

    private final FormulaEvaluator evaluator;

    public WorkbookProcessor(Workbook workbook) {
        this.workbook = workbook;
        evaluator = (workbook instanceof HSSFWorkbook) ? new HSSFFormulaEvaluator((HSSFWorkbook) this.workbook) : new XSSFFormulaEvaluator((XSSFWorkbook) this.workbook);
    }

    public void processWorkbook() {
        int numOfSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<List<String>> curatedSheetData = processSheet(sheet);
        }
        return;
    }

    private List<List<String>> processSheet(Sheet sheet) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        List<List<String>> curatedSheetData = new LinkedList<>();

        // process all the empty lines before the first row.
        for (int i=0; i<firstRowNum; i++) {
            List<String> emptyRow = new LinkedList<>();
            curatedSheetData.add(emptyRow);
        }

        for (int i = firstRowNum; i < lastRowNum; i++) {
            Row row = sheet.getRow(i);
            List<String> curatedRow = new LinkedList<>();
            if (row == null) {
                // if the row is undefined, deem as an empty row.
                curatedSheetData.add(curatedRow);
                continue;
            }

            int firstCellNum = row.getFirstCellNum();
            int lastCellNum = row.getLastCellNum();
            for (int j = 0; j < firstCellNum; j++) {
                // for the cells starting before the firstCellNum, deem as an empty cell.
                curatedRow.add("");
            }

            for (int j = firstCellNum; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    curatedRow.add("");
                    continue;
                }
                CellType cellType = cell.getCellType();
                String value = getCellStringValue(cell, cellType);
                curatedRow.add(value);
            }
            curatedSheetData.add(curatedRow);
        }
        return curatedSheetData;
    }

    private String getCellStringValue(Cell cell, CellType cellType) {
        String value;
        DataFormatter dataFormatter = new DataFormatter();
        switch (cellType) {
            case FORMULA:
                value = dataFormatter.formatCellValue(cell, evaluator);
                break;
            default:
                value = dataFormatter.formatCellValue(cell);
        }
        return value;
    }

    private void padSeparators() {

    }
}
