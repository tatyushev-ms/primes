package com.tatyushevms.primes;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class SimpleRowHandler implements RowHandler {

    @Override
    public Integer handle(final Row row) {
        final Cell cell = row.getCell(1);

        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.STRING) {
            final String value = cell.getStringCellValue();
            return Integer.parseInt(value);
        }

        return null;
    }

}
