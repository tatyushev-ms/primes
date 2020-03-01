package com.tatyushevms.primes;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.Collection;

public interface OoxmlReader {

    Collection<Integer> read(Workbook workbook);

}
