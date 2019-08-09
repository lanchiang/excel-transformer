package de.hpi.isg;

import de.hpi.isg.io.SheetToTextFileWriter;
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
            System.out.println(workbook.getSheetAt(i).getSheetName());

            Sheet sheet = workbook.getSheetAt(i);

            SheetVisibility visibility = workbook.getSheetVisibility(i);
            if (visibility != SheetVisibility.VISIBLE) {
                continue;
            }

            List<List<String>> curatedSheetData = processSheet(sheet);
            WrappedSheet wrappedSheet = new WrappedSheet(workbook.getSheetName(i), curatedSheetData);

            try {
                SheetToTextFileWriter writer = new SheetToTextFileWriter(wrappedSheet.getSheetFileName());
                for (List<String> curatedRow : curatedSheetData) {
                    writer.writeLine(curatedRow);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<List<String>> processSheet(Sheet sheet) {
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
