package com.tatyushevms.primes;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.stream.IntStream;

import static com.tatyushevms.primes.TestUtils.testClasspathResourceAsStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SimpleOoxmlReader tests")
class SimpleOoxmlReaderTest {
    
    @Mock
    private RowHandler rowHandler;
    
    @InjectMocks
    private SimpleOoxmlReader ooxmlReader;
    
    private static Workbook emptyExcelWorkbook;
    private static Workbook excelWorkbookWithEmptySheet;
    private static Workbook excelWorkbook;
    
    @BeforeAll
    static void setUp() throws Exception {
        emptyExcelWorkbook = new XSSFWorkbook(testClasspathResourceAsStream("empty.xlsx"));
        excelWorkbookWithEmptySheet = new XSSFWorkbook(testClasspathResourceAsStream("with-empty-sheet.xlsx"));
        excelWorkbook = new XSSFWorkbook(testClasspathResourceAsStream("normal.xlsx"));
    }
    
    @AfterAll
    static void tearDown() throws Exception {
        emptyExcelWorkbook.close();
        excelWorkbookWithEmptySheet.close();
        excelWorkbook.close();
    }
    
    @Test
    @DisplayName("Data is located in the first sheet")
    void dataIsLocatedInSecondColumn() {
        final Workbook workbook = mock(Workbook.class);
        
        ooxmlReader.read(workbook);
        
        verify(workbook).getSheetAt(0);
    }
    
    @Test
    @DisplayName("RowHandler is used")
    void rowHandlerIsUsed() {
        ooxmlReader.read(excelWorkbook);
        
        verify(rowHandler, atLeastOnce()).handle(any());
    }
    
    @Test
    @DisplayName("Invalid data should be ignored")
    void invalidDataShouldBeIgnored() {
        when(rowHandler.handle(any())).thenThrow(new RuntimeException());
        
        assertDoesNotThrow(() -> ooxmlReader.read(excelWorkbook));
    }
    
    @Test
    @DisplayName("Return result as expected")
    void read() {
        final Incrementer answer = new Incrementer();
        when(rowHandler.handle(any())).then(answer);
        
        final Collection<Integer> result = ooxmlReader.read(excelWorkbook);
        
        final int number = answer.getNumber();
        verify(rowHandler, times(number)).handle(any());
        assertThat(result, containsInRelativeOrder(IntStream.range(1, number).boxed().toArray(Integer[]::new)));
    }
    
    @Nested
    @DisplayName("RowHandler is used the expected number of times")
    class RowHandlerIsUsedExpectedNumberOfTimes {
        
        @Test
        @DisplayName("Zero if there is no defined row")
        void cellTypeIsChecked1() {
            final Collection<Integer> result = ooxmlReader.read(excelWorkbookWithEmptySheet);
            
            verify(rowHandler, never()).handle(any());
            assertThat(result, is(empty()));
        }
        
        @Test
        @DisplayName("As expected")
        void cellTypeIsChecked2() {
            final Incrementer answer = new Incrementer();
            when(rowHandler.handle(any())).then(answer);
            
            final Collection<Integer> result = ooxmlReader.read(excelWorkbook);
            
            final int number = answer.getNumber();
            verify(rowHandler, times(number)).handle(any());
            assertThat(result, hasSize(number));
        }
        
    }
    
    @Nested
    @DisplayName("RowHandler is not used unnecessarily")
    class RowHandlerIsNotUsedUnnecessarily {
        
        @Test
        @DisplayName("There is no required sheet")
        void thereIsNoRequiredSheet() {
            try {
                ooxmlReader.read(emptyExcelWorkbook);
            } catch (Exception ignored) {
            }
            
            verifyNoInteractions(rowHandler);
        }
        
        @Test
        @DisplayName("There is no defined row")
        void thereIsNoDefinedRow() {
            ooxmlReader.read(excelWorkbookWithEmptySheet);
            
            verifyNoInteractions(rowHandler);
        }
        
    }
    
}
