package com.tatyushevms.primes;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static java.util.Collections.emptySet;

public class SimpleOoxmlReader implements OoxmlReader {

    private final RowHandler rowHandler;

    public SimpleOoxmlReader(RowHandler rowHandler) {
        this.rowHandler = rowHandler;
    }

    @Override
    public Collection<Integer> read(final Workbook workbook) {
        final Sheet sheet = workbook.getSheetAt(0);

        if (sheet == null) {
            return emptySet();
        }

        final Iterator<Row> iterator = sheet.rowIterator();
        if (!iterator.hasNext()) {
            return emptySet();
        }

        return read(iterator);
    }

    private Collection<Integer> read(final Iterator<Row> rowIterator) {
        final Collection<Integer> result = new LinkedList<>();

        while (rowIterator.hasNext()) {
            final Integer number;
            try {
                number = rowHandler.handle(rowIterator.next());
            } catch (Exception ignored) {
                continue;
            }
            if (number != null) {
                result.add(number);
            }
        }

        return result;
    }

}
