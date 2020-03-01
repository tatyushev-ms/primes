package com.tatyushevms.primes;

import com.tatyushevms.primes.TrialDivision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DisplayName("TrialDivision tests")
class TrialDivisionTest {

    private TrialDivision tester = new TrialDivision();

    @DisplayName("Not natural numbers, therefore not primes:")
    @ParameterizedTest(name = "{0}")
    @ValueSource(ints = {Integer.MIN_VALUE, -100, -11, -7, -5, -3, -2, -1, 0})
    void primeIsNaturalNumber(int number) {
        assertThat(tester.isPrime(number), is(equalTo(false)));
    }

    @Test
    @DisplayName("A prime is a natural number greater than 1")
    void oneIsNotPrime() {
        assertThat(tester.isPrime(1), is(equalTo(false)));
    }

    @DisplayName("Primes:")
    @ParameterizedTest(name = "{0}")
    @ValueSource(ints = {2, 3, 5, 443, 983, 7919})
    void primes(int number) {
        assertThat(tester.isPrime(number), is(equalTo(true)));
    }

    @DisplayName("Not primes:")
    @ParameterizedTest(name = "{0}")
    @ValueSource(ints = {4, 6, 25, 1000, 7777, 1048575})
    void notPrimes(int number) {
        assertThat(tester.isPrime(number), is(equalTo(false)));
    }

}
