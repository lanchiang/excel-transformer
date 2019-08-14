package de.hpi.isg.sampling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Lan Jiang
 * @since 2019-08-13
 */
public class ExcelFileSampling {

    private final String inputFolder;

    private final String outputFolder;

    public ExcelFileSampling(String inputFolder, String outputFolder) {
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
    }

    public void sampling() throws IOException {
        File[] files = new File(inputFolder).listFiles();

        assert files != null;
        Sampling sampling = new Sampling(100, files.length);


        for (File file : files) {
            if (file.getName().equals("DS_Store.")) {
                continue;
            }

            File output = new File(outputFolder + file.getName());

            if (sampling.shouldSample()) {
                Files.copy(Paths.get(file.getPath()), Paths.get(output.getPath()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ExcelFileSampling excelFileSampling = new ExcelFileSampling("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk",
                "/Users/Fuga/Documents/hpi/data/excel-files/data-gov-uk-50-samples/");
        excelFileSampling.sampling();
    }
}
