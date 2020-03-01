package com.tatyushevms.primes;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import com.github.blindpirate.extensions.CaptureSystemOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.tatyushevms.primes.TestUtils.testClasspathResourceFullName;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Execution tests")
class MainTest {
    
    @Test
    @DisplayName("Handles a missing file")
    @ExpectSystemExitWithStatus(1)
    @CaptureSystemOutput
    void handlesMissingFile(CaptureSystemOutput.OutputCapture outputCapture) {
        assertThrows(SecurityException.class, () -> Main.main(new String[]{"there-is-no-such-file.xlsx"}));
        
        outputCapture.expect(containsString("No such file or directory"));
    }
    
    @Test
    @DisplayName("Handles an invalid file")
    @ExpectSystemExitWithStatus(1)
    @CaptureSystemOutput
    void handlesInvalidFile(CaptureSystemOutput.OutputCapture outputCapture) {
        assertThrows(SecurityException.class, () -> Main.main(new String[]{testClasspathResourceFullName("invalid.xlsx")}));
        
        outputCapture.expect(containsString("No valid entries or contents found, this is not a valid OOXML (Office Open XML) file"));
    }
    
    @Test
    @DisplayName("Handles a file without sheet")
    @CaptureSystemOutput
    @ExpectSystemExitWithStatus(1)
    void handlesInvalidFile1(CaptureSystemOutput.OutputCapture outputCapture) {
        assertThrows(SecurityException.class, () -> Main.main(new String[]{testClasspathResourceFullName("empty.xlsx")}));
        
        outputCapture.expect(containsString("Sheet index (0) is out of range (no sheets)"));
    }
    
    @Test
    @DisplayName("The only input parameter is the data file")
    @CaptureSystemOutput
    void theOnlyInputParameterIsTheDataFile(CaptureSystemOutput.OutputCapture outputCapture) {
        Main.main(new String[]{testClasspathResourceFullName("normal.xlsx")});
        
        outputCapture.expect(containsString("5645657\n" +
                "15619\n" +
                "1234187\n" +
                "211\n" +
                "7\n" +
                "9788677\n" +
                "23311\n" +
                "54881\n"
        ));
    }
    
    @DisplayName("Handles invalid amount of parameters")
    @ParameterizedTest(name = ParameterizedTest.INDEX_PLACEHOLDER + ") " + ParameterizedTest.ARGUMENTS_PLACEHOLDER)
    @MethodSource("zeroOrMultipleParameters")
    @CaptureSystemOutput
    @ExpectSystemExitWithStatus(1)
    void handlesInvalidAmountOfParameters(String[] args, CaptureSystemOutput.OutputCapture outputCapture) {
        assertThrows(SecurityException.class, () -> Main.main(args));
        
        outputCapture.expect(containsString("An only input parameter was expected: a path of a xlsx file"));
    }
    
    static Stream<Arguments> zeroOrMultipleParameters() {
        return Stream.of(
                arguments((Object) new String[]{}),
                arguments((Object) new String[]{"lemon", "apple"})
        );
    }
    
}
