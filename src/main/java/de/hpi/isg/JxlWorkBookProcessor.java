package de.hpi.isg;

import jxl.Workbook;

/**
 * @author Lan Jiang
 * @since 2019-08-12
 */
public class JxlWorkBookProcessor extends WorkBookProcessor {

    private final Workbook workbook;

    public JxlWorkBookProcessor(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public void process() {

    }
}
