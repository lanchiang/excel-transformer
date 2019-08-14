package de.hpi.isg.utils;

import java.io.File;

/**
 * @author Lan Jiang
 * @since 2019-08-14
 */
public class FileUtils {

    private final static String MAC_DS_STORE = "DS_Store.";

    /**
     * Return true if the given file should not be processed by the caller function.
     *
     * @param file the file to be checked.
     * @return true if the given file should not be processed by the caller function
     */
    public static boolean shouldNotProcess(File file) {
        if (file.getName().equals(MAC_DS_STORE)) {
            return false;
        }
        return true;
    }
}
