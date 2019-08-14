package de.hpi.isg.analytics;

import de.hpi.isg.utils.FileUtils;

import java.io.File;

/**
 * This class represents the various statistics of input excel files.
 *
 * @author Lan Jiang
 * @since 2019-08-14
 */
public class ExcelFileStatistics {

    private final String inputFolderPath;

    public ExcelFileStatistics(String inputFolderPath) {
        this.inputFolderPath = inputFolderPath;
    }

    public void fileNameStatistics() {
        File[] inputFiles = new File(inputFolderPath).listFiles();

        assert inputFiles != null;
        for (File file : inputFiles) {
            if (FileUtils.shouldNotProcess(file)) {
                continue;
            }

            String fileName = file.getName();

            String[] splits = fileName.split("@");
            String excelFileName = splits[0];
            String sheetName = splits[1].split(".csv")[0];

        }
    }

    public static void main(String[] args) {
        ExcelFileStatistics excelFileStatistics = new ExcelFileStatistics("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk_converted");
        excelFileStatistics.fileNameStatistics();
    }
}
