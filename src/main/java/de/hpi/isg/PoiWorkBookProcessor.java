package de.hpi.isg;

import de.hpi.isg.concept.WrappedSheet;
import de.hpi.isg.exceptions.FormulaParseException;
import de.hpi.isg.io.SheetToTextFileWriter;
import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class PoiWorkBookProcessor extends WorkBookProcessor {

    private final Workbook workbook;

    private final FormulaEvaluator evaluator;

    @Getter
    private List<WrappedSheet> wrappedSheets = new LinkedList<>();

    public PoiWorkBookProcessor(Workbook workbook) {
        this.workbook = workbook;
        evaluator = (workbook instanceof HSSFWorkbook) ? new HSSFFormulaEvaluator((HSSFWorkbook)
                this.workbook) : new XSSFFormulaEvaluator((XSSFWorkbook) this.workbook);
    }

    @Override
    public void process() {
        int numOfSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numOfSheets; i++) {
            System.out.println(workbook.getSheetAt(i).getSheetName());

            Sheet sheet = workbook.getSheetAt(i);

            SheetVisibility visibility = workbook.getSheetVisibility(i);
            if (visibility != SheetVisibility.VISIBLE) {
                continue;
            }

            List<List<String>> curatedSheetData;
            try {
                curatedSheetData = processSheet(sheet);
            } catch (FormulaParseException ignored) {
                continue;
            }

            wrappedSheets.add(new WrappedSheet(workbook.getSheetName(i), curatedSheetData));
        }
    }

    private List<List<String>> processSheet(final Sheet sheet) throws FormulaParseException {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        List<List<String>> curatedSheetData = new LinkedList<>();

        int globalFirstCellNum = Integer.MAX_VALUE;
        int globalLastCellNum = 0;

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

            if (firstCellNum < globalFirstCellNum) {
                globalFirstCellNum = firstCellNum;
            }
            if (lastCellNum > globalLastCellNum) {
                globalLastCellNum = lastCellNum;
            }

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
        if (globalFirstCellNum < 0) {
            globalFirstCellNum = 0;
        }
        return padding(curatedSheetData, globalFirstCellNum, globalLastCellNum);
    }

    private String getCellStringValue(Cell cell, CellType cellType) throws FormulaParseException {
        String value = null;
        DataFormatter dataFormatter = new DataFormatter();
        if (cellType == CellType.FORMULA) {
            try {
                value = dataFormatter.formatCellValue(cell, evaluator);
            } catch (RuntimeException e) {
                throw new FormulaParseException(e);
            }
        } else {
            value = dataFormatter.formatCellValue(cell);
        }
        return value;
    }

    private List<List<String>> padding(List<List<String>> curatedSheetData, int globalFirstCellNum, int globalLastCellNum) {
        List<List<String>> normalized = new LinkedList<>();
        for (List<String> curatedRow : curatedSheetData) {
            if (curatedRow.size() == 0) {
                continue;
            }

            List<String> normalizedRow = curatedRow.subList(globalFirstCellNum, curatedRow.size());
            int tailOffset = globalLastCellNum - normalizedRow.size();
            for (int i = 0; i < tailOffset; i++) {
                normalizedRow.add("");
            }
            normalized.add(normalizedRow);
        }
        return normalized;
    }
}
