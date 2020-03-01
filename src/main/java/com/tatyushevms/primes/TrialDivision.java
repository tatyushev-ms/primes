package com.tatyushevms.primes;

public class TrialDivision implements PrimalityTester {

    @Override
    public boolean isPrime(final int number) {
        if (number <= 1) {
            return false;
        }
        final int bound = (int) Math.sqrt(number);
        for (int i = 2; i <= bound; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}
