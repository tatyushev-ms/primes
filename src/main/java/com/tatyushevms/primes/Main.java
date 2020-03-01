package com.tatyushevms.primes;

import org.apache.commons.math3.primes.Primes;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

public class Main {
    
    private static final OoxmlReader OOXML_READER = new SimpleOoxmlReader(new SimpleRowHandler());
    private static final PrimalityTester PRIMALITY_TESTER = "TRIAL".equals(System.getenv("ALG")) ? new TrialDivision() : Primes::isPrime;
    
    public static void main(final String[] args) {
        if (args.length != 1) {
            exitWithError("An only input parameter was expected: a path of a xlsx file");
            return;
        }
        
        handle(args[0]);
    }
    
    private static void handle(final String path) {
        final Collection<Integer> numbers;
        try (final FileInputStream file = new FileInputStream(path);
             final Workbook excelBook = new XSSFWorkbook(file)) {
            numbers = OOXML_READER.read(excelBook);
        } catch (IOException | RuntimeException e) {
            exitWithError(e.getMessage());
            return;
        }
        
        numbers.forEach(Main::printPrimes);
    }
    
    private static void printPrimes(final Integer i) {
        if (PRIMALITY_TESTER.isPrime(i)) {
            System.out.println(i);
        }
    }
    
    private static void exitWithError(final String message) {
        System.err.println(message);
        System.exit(1);
    }
    
}
