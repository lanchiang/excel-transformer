package de.hpi.isg;

import com.beust.jcommander.Parameter;
import de.hpi.isg.concept.WrappedSheet;
import de.hpi.isg.exceptions.ExcelFileReadingException;
import de.hpi.isg.io.ExcelFileReader;
import de.hpi.isg.io.SheetToTextFileWriter;
import de.hpi.isg.utils.JCommanderParser;

import java.io.*;
import java.util.Collection;

/**
 * The entry of this project.
 *
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class App {

    private Parameters parameters;

    private static final String OUTPUT_FILE_UTILITY_CHARACTER = "@";

    public static void main(String[] args) throws IOException {
        App.Parameters parameters = new App.Parameters();
        JCommanderParser.parseCommandLineAndExitOnError(parameters, args);
        new App(parameters).run();
    }

    public App(Parameters parameters) {
        this.parameters = parameters;
    }

    private void run() throws IOException {
        File[] inputExcels = new File(this.parameters.inputFolder).listFiles();

        File abnormalFileLogFile = new File(this.parameters.abnormalFileLogFile);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(abnormalFileLogFile));

        assert inputExcels != null;
        for (File excelFile : inputExcels) {

//            if (!excelFile.getName().equals("temporary_and_permanent_migration_transparency_data_q3_2014.xlsx")) {
//                continue;
//            }

            System.out.println(excelFile.getName());

            ExcelFileReader excelFileReader = new ExcelFileReader(excelFile.getPath());
            try {
                excelFileReader.digestExcelFile();
            } catch (ExcelFileReadingException | IOException e) {
                bufferedWriter.write(e.getMessage() + "\t" + e.getCause().toString());
                bufferedWriter.newLine();
                continue;
            }

            Collection<WrappedSheet> wrappedSheets = excelFileReader.getWrappedSheets();
            assert wrappedSheets != null;

            for (WrappedSheet sheet : wrappedSheets) {
                SheetToTextFileWriter writer = new SheetToTextFileWriter(
                        this.parameters.outputFolder +
                                File.separator +
                                excelFile.getName() +
                                OUTPUT_FILE_UTILITY_CHARACTER +
                                sheet.getSheetFileName());
                writer.write(sheet);
            }
        }

        bufferedWriter.close();
    }

    /**
     * Parameters for the excel transformation app.
     */
    public static class Parameters {

        @Parameter(names = "--input-folder", description = "The folder contains all the input excel files", required = true)
        protected String inputFolder;

        @Parameter(names = "--output-folder", description = "The folder that stores all the generated csv files", required = true)
        protected String outputFolder;

        @Parameter(names = "--abnormal", description = "The file that stores those unprocessable excel files.", required = true)
        protected String abnormalFileLogFile;
    }
}
