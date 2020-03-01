package com.tatyushevms.primes;

import com.tatyushevms.primes.SimpleRowHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("SimpleRowHandler tests")
class SimpleRowHandlerTest {

    private SimpleRowHandler rowHandler = new SimpleRowHandler();

    @Mock
    private Row row;
    @Mock
    private Cell cell;

    @Test
    @DisplayName("Data is located in the B column")
    void dataIsLocatedInSecondColumn() {
        rowHandler.handle(row);

        verify(row).getCell(1);
    }

    @Test
    @DisplayName("Cell type is checked")
    void cellTypeIsChecked() {
        when(row.getCell(1)).thenReturn(cell);

        rowHandler.handle(row);

        verify(cell).getCellType();
    }

    @Test
    @DisplayName("Data is entered as a text field")
    void dataIsEnteredAsText() {
        when(row.getCell(1)).thenReturn(cell);
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("123");

        rowHandler.handle(row);

        verify(cell).getStringCellValue();
    }

    @ParameterizedTest
    @DisplayName("Invalid data should be ignored")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n", "1a", "abc", " 123", "123 "})
    void invalidDataShouldBeIgnored(String value) {
        when(row.getCell(1)).thenReturn(cell);
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn(value);

        assertThrows(NumberFormatException.class, () -> rowHandler.handle(row));
    }

}
